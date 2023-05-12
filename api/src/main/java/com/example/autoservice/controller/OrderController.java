package com.example.autoservice.controller;

import com.example.autoservice.dto.mapper.OrderMapper;
import com.example.autoservice.dto.mapper.ProductMapper;
import com.example.autoservice.dto.order.OrderRequestDto;
import com.example.autoservice.dto.order.OrderResponseDto;
import com.example.autoservice.dto.product.ProductRequestDto;
import com.example.autoservice.model.Order;
import com.example.autoservice.service.OrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper mapper;
    private final ProductMapper productMapper;

    public OrderController(OrderService orderService, OrderMapper mapper,
                           ProductMapper productMapper) {
        this.orderService = orderService;
        this.mapper = mapper;
        this.productMapper = productMapper;
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
                                              @RequestBody ProductRequestDto product) {
        Order order = orderService.addProduct(id, productMapper.toModel(product));
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
            @RequestBody @ApiParam(value = "new status for order") String status) {

        return mapper.toDto(orderService.updateStatus(id, status));
    }

    @GetMapping("/price/{id}")
    @ApiOperation(value = "Get total price order")
    public BigDecimal getTotalPrice(
            @PathVariable @ApiParam(value = "id of order that you want to get total price")
            Long id) {

        return orderService.getPrice(id);
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "Get order by id")
    public OrderResponseDto getOrder(
            @PathVariable @ApiParam(value = "id of order that you want to get")
            Long id) {

        return mapper.toDto(orderService.getById(id));
    }

    @GetMapping
    @ApiOperation(value = "Get list of orders")
    public List<OrderResponseDto> getAll() {
        return orderService.getAll().stream()
                .map(mapper::toDto)
                .toList();
    }
}
