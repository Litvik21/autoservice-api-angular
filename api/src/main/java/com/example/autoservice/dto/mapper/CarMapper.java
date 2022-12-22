package com.example.autoservice.dto.mapper;

import com.example.autoservice.dto.car.CarRequestDto;
import com.example.autoservice.dto.car.CarResponseDto;
import com.example.autoservice.model.Car;
import com.example.autoservice.service.CarOwnerService;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {
    private final CarOwnerService ownerService;

    public CarMapper(CarOwnerService ownerService) {
        this.ownerService = ownerService;
    }

    public CarResponseDto toDto(Car car) {
        CarResponseDto dto = new CarResponseDto();
        dto.setId(car.getId());
        dto.setBrand(car.getBrand());
        dto.setModel(car.getModel());
        dto.setYear(car.getYear());
        dto.setNumber(car.getNumber());
        dto.setOwnerId(car.getOwner().getId());

        return dto;
    }

    public Car toModel(CarRequestDto requestDto) {
        Car car = new Car();
        car.setBrand(requestDto.getBrand());
        car.setModel(requestDto.getModel());
        car.setYear(requestDto.getYear());
        car.setNumber(requestDto.getNumber());
        car.setOwner(ownerService.getById(requestDto.getOwnerId()));

        return car;
    }
}
