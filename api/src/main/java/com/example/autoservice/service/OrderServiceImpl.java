package com.example.autoservice.service;

import com.example.autoservice.model.*;
import com.example.autoservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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

    public OrderServiceImpl(OrderRepository orderRepository, TaskService taskService, MechanicService mechanicService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.taskService = taskService;
        this.mechanicService = mechanicService;
        this.productService = productService;
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
