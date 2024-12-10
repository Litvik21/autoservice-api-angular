package com.example.autoservice.service;

import com.example.autoservice.model.Salary;
import com.example.autoservice.repository.SalaryRepository;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SalaryServiceImpl implements SalaryService {
    private final SalaryRepository salaryRepository;

    @Override
    public void save(Salary salary) {
        salaryRepository.save(salary);
    }

    @Override
    public List<Salary> getAll() {
        return salaryRepository.findAll();
    }

    @Override
    public List<Salary> findAllByMechanicId(Long mechanicId) {
        return salaryRepository.findAllByMechanicId(mechanicId);
    }
}
