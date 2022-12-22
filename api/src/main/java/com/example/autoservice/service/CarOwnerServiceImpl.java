package com.example.autoservice.service;

import java.util.List;
import com.example.autoservice.model.Car;
import com.example.autoservice.model.Order;
import com.example.autoservice.model.CarOwner;
import com.example.autoservice.repository.CarOwnerRepository;
import org.springframework.stereotype.Service;

@Service
public class CarOwnerServiceImpl implements CarOwnerService {
    private final CarOwnerRepository ownerRepository;

    public CarOwnerServiceImpl(CarOwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public CarOwner save(CarOwner owner) {
        return ownerRepository.save(owner);
    }

    @Override
    public CarOwner getById(Long id) {
        return ownerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find car owner by id:" + id));
    }

    @Override
    public CarOwner update(CarOwner owner) {
        return ownerRepository.save(owner);
    }

    @Override
    public List<Order> findAllOrdersById(Long id) {
        return getById(id).getOrders();
    }

    @Override
    public List<Car> findAllCarsById(Long id) {
        return getById(id).getCars();
    }

    @Override
    public List<CarOwner> getAll() {
        return ownerRepository.findAll();
    }
}
