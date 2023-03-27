package com.example.autoservice.dto.mapper;

import com.example.autoservice.dto.order.OrderRequestDto;
import com.example.autoservice.dto.order.OrderResponseDto;
import com.example.autoservice.model.Order;
import com.example.autoservice.model.Product;
import com.example.autoservice.model.Task;
import com.example.autoservice.service.CarService;
import com.example.autoservice.service.ProductService;
import com.example.autoservice.service.TaskService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    private final CarService carService;
    private final ProductService productService;
    private final TaskService taskService;

    public OrderMapper(CarService carService,
                       ProductService productService, TaskService taskService) {
        this.carService = carService;
        this.productService = productService;
        this.taskService = taskService;
    }

    public OrderResponseDto toDto(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(order.getId());
        dto.setCarId(order.getCar().getId());
        dto.setDescription(order.getDescription());
        dto.setDateReceived(order.getDateReceived());
        dto.setTaskIds(order.getTasks().stream()
                .map(Task::getId)
                .collect(Collectors.toList()));
        dto.setProductsIds(order.getProducts().stream()
                .map(Product::getId)
                .collect(Collectors.toList()));
        dto.setStatus(order.getStatus().name());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setDateFinished(order.getDateFinished());

        return dto;
    }

    public Order toModel(OrderRequestDto requestDto) {
        Order order = new Order();
        order.setCar(carService.getById(requestDto.getCarId()));
        order.setDescription(requestDto.getDescription());
        order.setDateReceived(LocalDate.now());
        order.setTasks(requestDto.getTaskIds().stream()
                .map(taskService::getById)
                .toList());
        order.setProducts(requestDto.getProductsIds().stream()
                .map(productService::getById)
                .toList());
        order.setStatus(Order.Status.valueOf(requestDto.getStatus().toUpperCase()));
        order.setTotalPrice(BigDecimal.valueOf(0));
        order.setDateFinished(requestDto.getDateFinished());

        return order;
    }
}
