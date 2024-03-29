package com.example.autoservice.service;

import com.example.autoservice.model.Order;
import com.example.autoservice.model.Product;
import com.example.autoservice.model.Task;
import com.example.autoservice.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    TaskServiceImpl taskService;
    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock
    private OrderRepository orderRepository;

    private Order order;

    private Product product;

    private List<Product> products;

    @BeforeEach
    void setUp() {
        product = new Product(1L, "title", BigDecimal.valueOf(660));
        Product product1 = new Product(1L, "title", BigDecimal.valueOf(300));
        Product product2 = new Product(1L, "title", BigDecimal.valueOf(134));
        Product product3 = new Product(1L, "title", BigDecimal.valueOf(443));
        products = List.of(product1, product2, product3);
        order = new Order(1L, null, "description", LocalDate.now(),
                Collections.emptyList(),
                products,
                Order.Status.PROCESS,
                BigDecimal.valueOf(887),
                LocalDate.now());
    }

    @Test
    void shouldReturnTotalPriceAndDiagnosticIsFree() {
        Task task = new Task();
        task.setPrice(BigDecimal.valueOf(400));
        task.setType(Task.TypeOfTask.OIL_CHANGE);
        Task task1 = new Task();
        task1.setPrice(BigDecimal.valueOf(500));
        task1.setType(Task.TypeOfTask.DIAGNOSTICS);
        Order order1 = new Order();
        order1.setId(4L);
        order1.setProducts(Collections.emptyList());
        order1.setTasks(List.of(task, task1));

        Mockito.when(taskService.findTaskByType(Task.TypeOfTask.DIAGNOSTICS)).thenReturn(Optional.of(task1));
        Mockito.when(orderRepository.findById(4L)).thenReturn(Optional.of(order1));
        BigDecimal actual = orderService.getPrice(order1.getId());
        Assertions.assertEquals(BigDecimal.valueOf(399.84), actual);
    }

    @Test
    void shouldReturnTotalPriceAndDiagnosticIsNotFree() {
        Task task = new Task();
        task.setPrice(BigDecimal.valueOf(500));
        task.setType(Task.TypeOfTask.DIAGNOSTICS);
        Order order1 = new Order();
        order1.setId(4L);
        order1.setProducts(Collections.emptyList());
        order1.setTasks(List.of(task));

        Mockito.when(taskService.findTaskByType(Task.TypeOfTask.DIAGNOSTICS)).thenReturn(Optional.of(task));
        Mockito.when(orderRepository.findById(4L)).thenReturn(Optional.of(order1));
        BigDecimal actual = orderService.getPrice(order1.getId());
        Assertions.assertEquals(BigDecimal.valueOf(499.9), actual);
    }
}