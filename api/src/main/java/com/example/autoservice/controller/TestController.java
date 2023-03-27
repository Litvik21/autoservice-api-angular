package com.example.autoservice.controller;

import com.example.autoservice.model.*;
import com.example.autoservice.service.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {
    private final CarService carService;
    private final CarOwnerService ownerService;
    private final MechanicService mechanicService;
    private final OrderService orderService;
    private final ProductService productService;
    private final TaskService taskService;

    public TestController(CarService carService, CarOwnerService ownerService,
                          MechanicService mechanicService, OrderService orderService,
                          ProductService productService, TaskService taskService) {
        this.carService = carService;
        this.ownerService = ownerService;
        this.mechanicService = mechanicService;
        this.orderService = orderService;
        this.productService = productService;
        this.taskService = taskService;
    }

    @GetMapping
    public String setUpTestData() {
        CarOwner owner = new CarOwner();
        owner.setName("Nazar");
        ownerService.save(owner);

        Car car = new Car();
        car.setBrand("Kia");
        car.setModel("Sportage");
        car.setYear("2019");
        car.setNumber("FKFO30329DKEN");
        car.setOwner(owner);
        carService.save(car);

        Mechanic mechanic = new Mechanic();
        mechanic.setName("Muhamed");
        mechanicService.save(mechanic);

        Task task = new Task();
        task.setType(Task.TypeOfTask.OIL_CHANGE);
        task.setMechanic(mechanic);
        task.setPrice(BigDecimal.valueOf(450));
        task.setPaymentStatus(Task.PaymentStatus.NOT_PAID);
        taskService.save(task);

        Product product = new Product();
        product.setTitle("OIL");
        product.setPrice(BigDecimal.valueOf(299));
        productService.save(product);

        Order order = new Order();
        order.setCar(car);
        order.setDescription("Change OIL");
        order.setTasks(List.of(task));
        order.setProducts(List.of(product));
        order.setStatus(Order.Status.RECEIVED);
        order.setDateFinished(LocalDate.of(2023, 3, 28));
        orderService.save(order);
        return "Done!";
    }
}
