package com.example.autoservice.service;

import java.util.List;
import com.example.autoservice.model.Car;
import com.example.autoservice.model.Order;
import com.example.autoservice.model.CarOwner;

public interface CarOwnerService {
    CarOwner save(CarOwner owner);

    CarOwner getById(Long id);

    CarOwner update(CarOwner owner);

    List<Order> findAllOrdersById(Long id);

    List<Car> findAllCarsById(Long id);

    List<CarOwner> getAll();
}
