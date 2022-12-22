package com.example.autoservice.dto.mapper;

import com.example.autoservice.dto.mechanic.MechanicRequestDto;
import com.example.autoservice.dto.mechanic.MechanicResponseDto;
import com.example.autoservice.model.Mechanic;
import com.example.autoservice.model.Order;
import com.example.autoservice.service.OrderService;
import org.springframework.stereotype.Component;

@Component
public class MechanicMapper {
    private final OrderService orderService;

    public MechanicMapper(OrderService orderService) {
        this.orderService = orderService;
    }

    public MechanicResponseDto toDto(Mechanic mechanic) {
        MechanicResponseDto dto = new MechanicResponseDto();
        dto.setId(mechanic.getId());
        dto.setName(mechanic.getName());
        dto.setFinishedOrdersId(mechanic.getFinishedOrders().stream()
                .map(Order::getId)
                .toList());

        return dto;
    }

    public Mechanic toModel(MechanicRequestDto requestDto) {
        Mechanic mechanic = new Mechanic();
        mechanic.setName(requestDto.getName());
        mechanic.setFinishedOrders(requestDto.getFinishedOrdersId().stream()
                .map(orderService::getById)
                .toList());

        return mechanic;
    }
}
