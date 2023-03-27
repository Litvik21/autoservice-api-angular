package com.example.autoservice.service;

import com.example.autoservice.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task save(Task task);

    Task update(Task task);

    Task updateStatus(Long taskId, String statusPaid);

    Task getById(Long id);

    List<Task> findTasksByMechanicId(Long mechanicId);

    Optional<Task> findTaskByType(Task.TypeOfTask type);

    List<Task> getAll();
}
