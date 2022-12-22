package com.example.autoservice.controller;

import java.math.BigDecimal;
import java.util.List;
import com.example.autoservice.dto.order.OrderRequestDto;
import com.example.autoservice.dto.order.OrderResponseDto;
import com.example.autoservice.dto.mapper.OrderMapper;
import com.example.autoservice.model.Order;
import com.example.autoservice.model.Product;
import com.example.autoservice.service.OrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper mapper;

    public OrderController(OrderService orderService, OrderMapper mapper) {
        this.orderService = orderService;
        this.mapper = mapper;
    }

    @PostMapping
    @ApiOperation(value = "Save order to DB")
    public OrderResponseDto save(@RequestBody OrderRequestDto requestDto) {
        Order order = mapper.toModel(requestDto);
        return mapper.toDto(orderService.save(order));
    }

    @PostMapping("/add-product/{id}")
    @ApiOperation(value = "add product to order by order id")
    public OrderResponseDto addProductToOrder(@PathVariable Long id,
                                              @RequestBody Product product) {
        Order order = orderService.addProduct(id, product);
        return mapper.toDto(order);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update order by id")
    public OrderResponseDto update(
            @PathVariable @ApiParam(value = "id of order that you want to update")
            Long id,
            @RequestBody OrderRequestDto requestDto) {

        Order order = mapper.toModel(requestDto);
        order.setId(id);
        return mapper.toDto(orderService.update(order));
    }

    @PutMapping("/update-status/{id}")
    @ApiOperation(value = "Update order status by id")
    public OrderResponseDto updateStatus(
            @PathVariable @ApiParam(value = "id of order that you want to update status")
            Long id,
            @RequestParam @ApiParam(value = "new status for order") String status) {

        return mapper.toDto(orderService.updateStatus(id, Order.Status.valueOf(status)));
    }

    @GetMapping("/price/{id}")
    @ApiOperation(value = "Get total price order")
    public BigDecimal getTotalPrice(
            @PathVariable @ApiParam(value = "id of order that you want to get total price")
            Long id) {

        return orderService.getPrice(id);
    }

    @GetMapping
    @ApiOperation(value = "Get list of orders")
    public List<OrderResponseDto> getAll() {
        return orderService.getAll().stream()
                .map(mapper::toDto)
                .toList();
    }
}
