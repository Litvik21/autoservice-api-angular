package com.example.autoservice.service;

import com.example.autoservice.model.Order;
import com.example.autoservice.model.Product;
import com.example.autoservice.model.Task;
import com.example.autoservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private static final double PERCENT_FOR_PRODUCTS = 0.01;
    private static final double PERCENT_FOR_TASKS = 0.02;
    private final OrderRepository orderRepository;
    private final TaskService taskService;

    public OrderServiceImpl(OrderRepository orderRepository, TaskService taskService) {
        this.orderRepository = orderRepository;
        this.taskService = taskService;
    }

    @Override
    public Order save(Order order) {
        order.setDateReceived(LocalDate.now());
        return orderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order addProduct(Long orderId, Product product) {
        Order order = getById(orderId);
        List<Product> products = order.getProducts();
        products.add(product);
        order.setProducts(products);
        return save(order);
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
        if (order.getStatus() == Order.Status.SUCCESSFULLY_COMPLETED
                || order.getStatus() == Order.Status.NOT_SUCCESSFULLY_COMPLETED) {
            order.setDateFinished(LocalDate.now());
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
