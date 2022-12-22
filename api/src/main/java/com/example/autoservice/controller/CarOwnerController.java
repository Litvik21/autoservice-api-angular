package com.example.autoservice.controller;

import java.util.List;
import java.util.stream.Collectors;
import com.example.autoservice.dto.order.OrderResponseDto;
import com.example.autoservice.dto.owner.CarOwnerRequestDto;
import com.example.autoservice.dto.owner.CarOwnerResponseDto;
import com.example.autoservice.dto.mapper.OrderMapper;
import com.example.autoservice.dto.mapper.CarOwnerMapper;
import com.example.autoservice.model.CarOwner;
import com.example.autoservice.service.CarOwnerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/car-owners")
public class CarOwnerController {
    private final CarOwnerService ownerService;
    private final CarOwnerMapper mapper;
    private final OrderMapper orderMapper;

    public CarOwnerController(CarOwnerService ownerService, CarOwnerMapper mapper,
                              OrderMapper orderMapper) {
        this.ownerService = ownerService;
        this.mapper = mapper;
        this.orderMapper = orderMapper;
    }

    @PostMapping
    @ApiOperation(value = "Save car owner to DB")
    public CarOwnerResponseDto save(@RequestBody CarOwnerRequestDto requestDto) {
        CarOwner owner = ownerService.save(mapper.toModel(requestDto));
        return mapper.toDto(owner);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update car owner by id")
    public CarOwnerResponseDto update(
            @PathVariable @ApiParam(value = "id of car owner that you want to update")
            Long id,
            @RequestBody CarOwnerRequestDto requestDto) {

        CarOwner owner = mapper.toModel(requestDto);
        owner.setId(id);
        return mapper.toDto(ownerService.update(owner));
    }

    @GetMapping("/orders/{id}")
    @ApiOperation(value = "Get all car owner orders by id")
    public List<OrderResponseDto> getAllOrders(
            @PathVariable @ApiParam(value = "id of car owner that you want to get all orders")
            Long id) {

        return ownerService.findAllOrdersById(id).stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping
    @ApiOperation(value = "Get list of car owners")
    public List<CarOwnerResponseDto> getAll() {
        return ownerService.getAll().stream()
                .map(mapper::toDto)
                .toList();
    }
}
