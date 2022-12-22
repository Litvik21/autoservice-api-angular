package com.example.autoservice.service;

import java.math.BigDecimal;
import java.util.List;
import com.example.autoservice.model.Task;
import com.example.autoservice.model.Mechanic;
import com.example.autoservice.model.Order;
import com.example.autoservice.repository.MechanicRepository;
import org.springframework.stereotype.Service;

@Service
public class MechanicServiceImpl implements MechanicService {
    private final MechanicRepository mechanicRepository;
    private final TaskService taskService;

    public MechanicServiceImpl(MechanicRepository mechanicRepository, TaskService taskService) {
        this.mechanicRepository = mechanicRepository;
        this.taskService = taskService;
    }

    @Override
    public Mechanic save(Mechanic mechanic) {
        return mechanicRepository.save(mechanic);
    }

    @Override
    public Mechanic update(Mechanic mechanic) {
        return mechanicRepository.save(mechanic);
    }

    @Override
    public List<Order> getOrders(Long mechanicId) {
        return getById(mechanicId).getFinishedOrders();
    }

    @Override
    public BigDecimal getSalary(Long mechanicId) {
        List<Task> tasksByMechanicId = taskService.findTasksByMechanicId(mechanicId);
        double totalPriceForJob = tasksByMechanicId.stream()
                .map(Task::getPrice)
                .mapToDouble(BigDecimal::doubleValue)
                .sum();
        double masterSalary = totalPriceForJob * 0.4;

        updateStatusOfJobMaster(tasksByMechanicId);
        return BigDecimal.valueOf(masterSalary);
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

    private void updateStatusOfJobMaster(List<Task> tasks) {
        for (Task task : tasks) {
            taskService.updateStatus(task.getId(), Task.PaymentStatus.PAID);
        }
    }
}
