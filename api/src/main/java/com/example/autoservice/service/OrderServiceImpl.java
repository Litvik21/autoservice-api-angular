package com.example.autoservice.service;

import com.example.autoservice.model.*;
import com.example.autoservice.repository.OrderRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private static final double PERCENT_FOR_PRODUCTS = 0.01;
    private static final double PERCENT_FOR_TASKS = 0.02;
    private final OrderRepository orderRepository;
    private final TaskService taskService;
    private final MechanicService mechanicService;
    private final ProductService productService;
    private final SalaryService salaryService;

    public OrderServiceImpl(OrderRepository orderRepository, TaskService taskService, MechanicService mechanicService, ProductService productService, SalaryService salaryService) {
        this.orderRepository = orderRepository;
        this.taskService = taskService;
        this.mechanicService = mechanicService;
        this.productService = productService;
        this.salaryService = salaryService;
    }

    @Override
    public Order save(Order order) {
        order.setDateReceived(LocalDate.now());
        order.setProducts(order.getProducts().stream().distinct().collect(Collectors.toList()));
        try {
            Order saved = orderRepository.save(order);
            order.getTasks().stream().forEach(task -> {
                task.setPaymentStatus(Task.PaymentStatus.NOT_PAID);
                taskService.save(task);
            });
            return saved;
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public Order update(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order addProduct(Long orderId, Product product) {
        Order order = getById(orderId);
        List<Product> products = order.getProducts();
        System.out.println(productService.getById(product.getId()));
        products.add(product);
        order.setProducts(products);
        orderRepository.save(order);

        return order;
    }

    @Override
    public Order removeProduct(Long orderId, Long productId) {
        Order order = getById(orderId);
        List<Product> products = order.getProducts();
        products.remove(productService.getById(productId));
        order.setProducts(products);
        orderRepository.save(order);

        return order;
    }

    @Override
    public Order updateStatus(Long orderId, String status) {
        Order order = getById(orderId);
        order.setStatus(Order.Status.valueOf(status.toUpperCase()));
        checkStatus(order);
        return orderRepository.save(order);
    }

    @Override
    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find order by id:" + id));
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Product> getAllProducts(Long id) {
        return getById(id).getProducts();
    }

    @Override
    public List<Order> getByUser(Long userId) {
        return getAll().stream()
                .filter(order -> order.getCar().getOwner().getId().equals(userId))
                .toList();
    }

    @Override
    public List<Order> getFinishedByMechanicId(Long userId) {
        return getAll().stream()
                .filter(order -> order.getStatus().equals(Order.Status.SUCCESSFULLY_COMPLETED) ||
                        order.getStatus().equals(Order.Status.NOT_SUCCESSFULLY_COMPLETED))
                .filter(order -> order.getTasks().stream()
                        .anyMatch(task -> task.getMechanic().getId().equals(userId)))
                .toList();
    }


    @Override
    public BigDecimal getPrice(Long id) {
        Order order = getById(id);
        checkTypeOfTaskOnDiagnostic(order);
        updateTotalPrice(order);
        return order.getTotalPrice();
    }

    @Override
    public byte[] getSalaryReport() {
        mechanicService.getAll().stream().map(m -> mechanicService.getSalary(m.getId())).toList();

        List<Salary> salaries = salaryService.getAll();

        // Создаем Map, где ключ - id механика, значение - список Orders

        Map<Long, List<Order>> resultMap = new HashMap<>();
        for (Mechanic m : salaries.stream().map(Salary::getMechanic).toList()) {
            List<Order> finishedByMechanicId = getFinishedByMechanicId(m.getId());
            resultMap.put(m.getId(), finishedByMechanicId);
        }

//        Map<Long, List<Order>> resultMap = salaries.stream()
//                .map(Salary::getMechanic)
//                .collect(Collectors.toMap(
//                        Mechanic::getId,
//                        mechanic -> getFinishedByMechanicId(mechanic.getId())
//                ));

        // Создаем Excel-документ
        try (Workbook workbook = new HSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Salary Report");
            int rowIndex = 0;

            // Заголовки таблицы
            Row headerRow = sheet.createRow(rowIndex++);
            headerRow.createCell(0).setCellValue("Mechanic Name");
            headerRow.createCell(1).setCellValue("Order Description");
            headerRow.createCell(2).setCellValue("Car Details");
            headerRow.createCell(3).setCellValue("Order Price");
            headerRow.createCell(4).setCellValue("Salary");
            headerRow.createCell(5).setCellValue("Period");

            // Формат даты
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // Заполняем таблицу
            for (Salary salary : salaries) {
                Mechanic mechanic = salary.getMechanic();
                String mechanicName = mechanic.getName() + " " + mechanic.getLastName();
                String period = salary.getPeriodFrom().format(formatter) + " - " + salary.getPeriodTo().format(formatter);

                List<Order> orders = resultMap.get(mechanic.getId());
                if (orders != null && !orders.isEmpty()) {
                    for (Order order : orders) {
                        Row row = sheet.createRow(rowIndex++);
                        row.createCell(0).setCellValue(mechanicName);
                        row.createCell(1).setCellValue(order.getDescription());
                        row.createCell(2).setCellValue(order.getCar().getBrand() + " " +
                                order.getCar().getModel() + " " + order.getCar().getYear());
                        row.createCell(3).setCellValue(getPrice(order.getId()).toString());
                        row.createCell(4).setCellValue(salary.getSalary().toString());
                        row.createCell(5).setCellValue(period);
                    }
                } else {
                    // Если нет заказов, оставляем строки пустыми для Orders
                    Row row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(mechanicName);
                    row.createCell(1).setCellValue("No Orders");
                    row.createCell(2).setCellValue("");
                    row.createCell(3).setCellValue("");
                    row.createCell(4).setCellValue(salary.getSalary().toString());
                    row.createCell(5).setCellValue(period);
                }
            }

            // Автоматическое изменение ширины колонок
            for (int i = 0; i < 6; i++) {
                sheet.autoSizeColumn(i);
            }

            // Преобразуем Workbook в byte[]
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                workbook.write(outputStream);
                return outputStream.toByteArray();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while generating salary report", e);
        }
    }

    private void checkTypeOfTaskOnDiagnostic(Order order) {
        List<Task> tasks = order.getTasks();
        Optional<Task> taskByTypeDiagnostic = taskService
                .findTaskByType(Task.TypeOfTask.DIAGNOSTICS);
        if (tasks.size() == 1
                && taskByTypeDiagnostic.isPresent()) {
            tasks.get(0).setPrice(BigDecimal.valueOf(500));
        } else if (tasks.size() > 1
                && taskByTypeDiagnostic.isPresent()) {
            taskByTypeDiagnostic.get().setPrice(BigDecimal.valueOf(0));
        }
    }

    private void checkStatus(Order order) {
        if (order.getStatus() == Order.Status.PAID) {
            order.setStatus(Order.Status.SUCCESSFULLY_COMPLETED);
        }

        if (order.getStatus() == Order.Status.SUCCESSFULLY_COMPLETED
                || order.getStatus() == Order.Status.NOT_SUCCESSFULLY_COMPLETED) {
            order.setDateFinished(LocalDate.now());
            List<Task> tasks = order.getTasks();

            for (Task task : tasks) {
                Mechanic mechanic = task.getMechanic();
                if (order.getStatus() != Order.Status.PAID ||
                        order.getStatus() != Order.Status.PROCESS ||
                        order.getStatus() != Order.Status.RECEIVED ) {
                    mechanic.setStatus(Mechanic.Status.FREE);
                    mechanicService.save(mechanic);
                }
                task.setPaymentStatus(Task.PaymentStatus.PAID);
                taskService.update(task);
            }
        }
    }

    private void updateTotalPrice(Order order) {
        double totalPriceProducts = order.getProducts().stream()
                .map(Product::getPrice)
                .mapToDouble(BigDecimal::doubleValue)
                .sum();
        double totalPriceJobs = order.getTasks().stream()
                .map(Task::getPrice)
                .mapToDouble(BigDecimal::doubleValue)
                .sum();
        order.setTotalPrice(getTotalPriceWithSale(order, totalPriceProducts, totalPriceJobs));
        orderRepository.save(order);
    }

    private BigDecimal getTotalPriceWithSale(Order order, double totalPriceProducts,
                           double totalPriceJobs) {
        double saleOfProducts = order.getProducts().size() * PERCENT_FOR_PRODUCTS;
        double saleOfTasks = order.getTasks().size() * PERCENT_FOR_TASKS;
        double totalPriceWithOutSale = totalPriceJobs + totalPriceProducts;
        double sale = (totalPriceJobs + totalPriceProducts)
                * (saleOfProducts + saleOfTasks) / 100;
        return BigDecimal.valueOf(totalPriceWithOutSale - sale);
    }
}
