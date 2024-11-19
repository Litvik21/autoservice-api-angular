package com.example.autoservice.repository;

import com.example.autoservice.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaryRepository extends JpaRepository<Salary, Long> {
    List<Salary> findAllByMechanicId(Long mechanicId);
}
