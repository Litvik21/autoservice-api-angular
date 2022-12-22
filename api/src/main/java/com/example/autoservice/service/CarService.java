package com.example.autoservice.service;

import com.example.autoservice.model.Car;

import java.util.List;

public interface CarService {
    Car save(Car car);

    Car getById(Long id);

    Car update(Car car);

    List<Car> getAll();
}
