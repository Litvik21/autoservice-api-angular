package com.example.autoservice.dto.mapper;

import com.example.autoservice.dto.owner.CarOwnerRequestDto;
import com.example.autoservice.dto.owner.CarOwnerResponseDto;
import com.example.autoservice.model.Car;
import com.example.autoservice.model.CarOwner;
import com.example.autoservice.model.Order;
import com.example.autoservice.service.CarService;
import com.example.autoservice.service.OrderService;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CarOwnerMapper {
    private final CarService carService;

    private final OrderService orderService;

    public CarOwnerMapper(CarService carService,
                          OrderService orderService) {
        this.carService = carService;
        this.orderService = orderService;
    }

    public CarOwnerResponseDto toDto(CarOwner owner) {
        CarOwnerResponseDto dto = new CarOwnerResponseDto();
        dto.setId(owner.getId());
        dto.setName(owner.getName());
        if (owner.getOrders() != null) {
            dto.setOrdersId(owner.getOrders().stream()
                    .map(Order::getId)
                    .collect(Collectors.toList()));
        }
        if (owner.getCars() != null) {
            dto.setCarsId(owner.getCars().stream()
                    .map(Car::getId)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public CarOwner toModel(CarOwnerRequestDto requestDto) {
        CarOwner owner = new CarOwner();
        owner.setName(requestDto.getName());
        if (requestDto.getCarsId() != null) {
            owner.setCars(requestDto.getCarsId().stream()
                    .map(carService::getById)
                    .toList());
        }
        if (requestDto.getOrdersId() != null) {
            owner.setOrders(requestDto.getOrdersId().stream()
                    .map(orderService::getById)
                    .toList());
        }

        return owner;
    }
}
