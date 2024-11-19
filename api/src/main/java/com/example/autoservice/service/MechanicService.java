package com.example.autoservice.service;

import com.example.autoservice.model.Mechanic;
import com.example.autoservice.model.Order;

import java.math.BigDecimal;
import java.util.List;

public interface MechanicService {
    Mechanic save(Mechanic master);

    Mechanic update(Mechanic master);

    BigDecimal getSalary(Long masterId);

    Mechanic getById(Long id);

    List<Mechanic> getAll();

    List<Mechanic> getAllFree();
}
