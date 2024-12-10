package com.example.autoservice.service;

import com.example.autoservice.model.Salary;

import java.util.List;

public interface SalaryService {
    void save(Salary salary);

    List<Salary> getAll();

    List<Salary> findAllByMechanicId(Long mechanicId);
}
