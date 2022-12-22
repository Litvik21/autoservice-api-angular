package com.example.autoservice.controller;

import java.util.List;
import com.example.autoservice.dto.car.CarRequestDto;
import com.example.autoservice.dto.car.CarResponseDto;
import com.example.autoservice.dto.mapper.CarMapper;
import com.example.autoservice.model.Car;
import com.example.autoservice.service.CarService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cars")
public class CarController {
    private final CarService carService;
    private final CarMapper mapper;


    public CarController(CarService carService, CarMapper mapper) {
        this.carService = carService;
        this.mapper = mapper;
    }

    @PostMapping
    @ApiOperation(value = "Save a new car to DB")
    public CarResponseDto save(@RequestBody CarRequestDto carRequest) {
        Car car = carService.save(mapper.toModel(carRequest));
        return mapper.toDto(car);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update car by id")
    public CarResponseDto update(
            @PathVariable @ApiParam(value = "id of car that you want to update")
            Long id,
            @RequestBody CarRequestDto carRequest) {

        Car car = mapper.toModel(carRequest);
        car.setId(id);
        return mapper.toDto(carService.update(car));
    }

    @GetMapping
    @ApiOperation(value = "Get list of cars")
    public List<CarResponseDto> getAll() {
        return carService.getAll().stream()
                .map(mapper::toDto)
                .toList();
    }
}
