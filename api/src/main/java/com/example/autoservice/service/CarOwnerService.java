package com.example.autoservice.service;

import com.example.autoservice.model.CarOwner;
import com.example.autoservice.model.Order;

import java.util.List;

public interface CarOwnerService {
    CarOwner save(CarOwner owner);

    CarOwner getById(Long id);

    CarOwner update(CarOwner owner);

    List<Order> findAllOrdersById(Long id);

    List<CarOwner> getAll();
}
