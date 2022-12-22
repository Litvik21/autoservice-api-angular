package com.example.autoservice.controller;

import java.math.BigDecimal;
import java.util.List;
import com.example.autoservice.dto.mechanic.MechanicRequestDto;
import com.example.autoservice.dto.mechanic.MechanicResponseDto;
import com.example.autoservice.dto.order.OrderResponseDto;
import com.example.autoservice.dto.mapper.MechanicMapper;
import com.example.autoservice.dto.mapper.OrderMapper;
import com.example.autoservice.model.Mechanic;
import com.example.autoservice.service.MechanicService;
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
@RequestMapping("/mechanics")
public class MechanicController {
    private final MechanicService mechanicService;
    private final MechanicMapper mapper;
    private final OrderMapper orderMapper;

    public MechanicController(MechanicService mechanicService, MechanicMapper mapper, OrderMapper orderMapper) {
        this.mechanicService = mechanicService;
        this.mapper = mapper;
        this.orderMapper = orderMapper;
    }

    @PostMapping
    @ApiOperation(value = "Save mechanic to DB")
    public MechanicResponseDto save(@RequestBody MechanicRequestDto requestDto) {
        Mechanic mechanic = mapper.toModel(requestDto);
        return mapper.toDto(mechanicService.save(mechanic));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update mechanic by id")
    public MechanicResponseDto update(
            @PathVariable @ApiParam(value = "id of mechanic that you want to update")
            Long id,
            @RequestBody MechanicRequestDto requestDto) {

        Mechanic mechanic = mapper.toModel(requestDto);
        mechanic.setId(id);
        return mapper.toDto(mechanicService.update(mechanic));
    }

    @GetMapping("/{id}/finished-orders")
    @ApiOperation(value = "Get list orders of mechanic by id")
    public List<OrderResponseDto> getFinishedOrders(
            @PathVariable @ApiParam(value = "id of mechanic that you want to get list of orders")
            Long id) {

        return mechanicService.getOrders(id).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get salary for mechanic by id")
    public BigDecimal getSalary(
            @PathVariable @ApiParam(value = "id of mechanic that you want to get salary")
            Long id) {

        return mechanicService.getSalary(id);
    }

    @GetMapping
    @ApiOperation(value = "Get list of mechanics")
    public List<MechanicResponseDto> getAll() {
        return mechanicService.getAll().stream()
                .map(mapper::toDto)
                .toList();
    }
}
