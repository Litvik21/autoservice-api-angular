package com.example.autoservice.service;

import com.example.autoservice.model.Task;
import com.example.autoservice.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task update(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task updateStatus(Long taskId, String statusPaid) {
        Task taskToUpdate = getById(taskId);
        taskToUpdate.setPaymentStatus(Task.PaymentStatus.valueOf(statusPaid.toUpperCase()));
        return taskRepository.save(taskToUpdate);
    }

    @Override
    public List<Task> findTasksByMechanicId(Long mechanicId) {
        return taskRepository.findTasksByMechanicId(mechanicId);
    }

    @Override
    public Task getById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find task with this id:" + id));
    }

    @Override
    public Optional<Task> findTaskByType(Task.TypeOfTask type) {
        return taskRepository.findByType(type);
    }

    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }
}
