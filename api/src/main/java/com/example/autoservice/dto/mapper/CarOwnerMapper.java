package com.example.autoservice.dto.mapper;

import java.util.stream.Collectors;
import com.example.autoservice.dto.owner.CarOwnerRequestDto;
import com.example.autoservice.dto.owner.CarOwnerResponseDto;
import com.example.autoservice.model.Car;
import com.example.autoservice.model.Order;
import com.example.autoservice.model.CarOwner;
import com.example.autoservice.service.CarService;
import com.example.autoservice.service.OrderService;
import com.example.autoservice.service.CarOwnerService;
import org.springframework.stereotype.Component;

@Component
public class CarOwnerMapper {
    private final CarOwnerService ownerService;
    private final CarService carService;

    private final OrderService orderService;

    public CarOwnerMapper(CarOwnerService ownerService, CarService carService,
                          OrderService orderService) {
        this.ownerService = ownerService;
        this.carService = carService;
        this.orderService = orderService;
    }

    public CarOwnerResponseDto toDto(CarOwner owner) {
        CarOwnerResponseDto dto = new CarOwnerResponseDto();
        dto.setId(owner.getId());
        dto.setOrdersId(owner.getOrders().stream()
                .map(Order::getId)
                .collect(Collectors.toList()));
        dto.setCarsId(owner.getCars().stream()
                .map(Car::getId)
                .collect(Collectors.toList()));

        return dto;
    }

    public CarOwner toModel(CarOwnerRequestDto requestDto) {
        CarOwner owner = new CarOwner();
        owner.setCars(requestDto.getCarsId().stream()
                .map(carService::getById)
                .toList());
        owner.setOrders(requestDto.getOrdersId().stream()
                .map(orderService::getById)
                .toList());

        return owner;
    }
}
