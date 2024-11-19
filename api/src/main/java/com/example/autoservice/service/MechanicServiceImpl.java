package com.example.autoservice.service;

import com.example.autoservice.model.Mechanic;
import com.example.autoservice.model.Order;
import com.example.autoservice.model.Salary;
import com.example.autoservice.model.Task;
import com.example.autoservice.repository.MechanicRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class MechanicServiceImpl implements MechanicService {
    private static final String PAID_OUT_STATUS = "Paid_Out";
    private static final double PERCENT_SALARY = 0.4;
    private final MechanicRepository mechanicRepository;
    private final TaskService taskService;
    private final SalaryService salaryService;

    public MechanicServiceImpl(MechanicRepository mechanicRepository, TaskService taskService, SalaryService salaryService) {
        this.mechanicRepository = mechanicRepository;
        this.taskService = taskService;
        this.salaryService = salaryService;
    }

    @Override
    public Mechanic save(Mechanic mechanic) {
        mechanic.setStatus(Mechanic.Status.FREE);
        return mechanicRepository.save(mechanic);
    }

    @Override
    public Mechanic update(Mechanic mechanic) {
        return mechanicRepository.save(mechanic);
    }

    @Override
    public BigDecimal getSalary(Long mechanicId) {
        List<Task> tasksByMechanicId = taskService.findTasksByMechanicId(mechanicId)
                .stream()
                .filter(t -> t.getPaymentStatus() == Task.PaymentStatus.PAID)
                .toList();

        double totalPriceForJob = tasksByMechanicId.stream()
                .map(Task::getPrice)
                .mapToDouble(BigDecimal::doubleValue)
                .sum();
        double masterSalary = totalPriceForJob * PERCENT_SALARY;
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);

        Salary salary = new Salary();
        salary.setMechanic(mechanicRepository.findById(mechanicId).get());
        salary.setSalary(BigDecimal.valueOf(masterSalary));
        salary.setPeriodFrom(startOfMonth);
        salary.setPeriodTo(LocalDate.now());
        salaryService.save(salary);

        LocalDate endOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());

        List<Salary> allByMechanicId = salaryService.findAllByMechanicId(mechanicId);
        BigDecimal totalMonthlySalary = allByMechanicId.stream()
                .filter(s -> !s.getPeriodFrom().isBefore(startOfMonth) && !s.getPeriodTo().isAfter(endOfMonth))
                .map(Salary::getSalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        updateStatusOfJobMaster(tasksByMechanicId);
        return totalMonthlySalary;
    }

    @Override
    public Mechanic getById(Long id) {
        return mechanicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find master by id:" + id));
    }

    @Override
    public List<Mechanic> getAll() {
        return mechanicRepository.findAll();
    }

    @Override
    public List<Mechanic> getAllFree() {
        return mechanicRepository.findAll()
                .stream()
                .filter(m -> m.getStatus().equals(Mechanic.Status.FREE))
                .toList();
    }

    private void updateStatusOfJobMaster(List<Task> tasks) {
        for (Task task : tasks) {
            taskService.updateStatus(task.getId(), PAID_OUT_STATUS);
        }
    }
}
