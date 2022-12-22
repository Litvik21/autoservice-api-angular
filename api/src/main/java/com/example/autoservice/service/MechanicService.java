package com.example.autoservice.service;

import java.math.BigDecimal;
import java.util.List;
import com.example.autoservice.model.Mechanic;
import com.example.autoservice.model.Order;

public interface MechanicService {
    Mechanic save(Mechanic master);

    Mechanic update(Mechanic master);

    List<Order> getOrders(Long masterId);

    BigDecimal getSalary(Long masterId);

    Mechanic getById(Long id);

    List<Mechanic> getAll();
}
