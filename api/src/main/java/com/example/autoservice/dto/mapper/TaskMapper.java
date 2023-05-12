package com.example.autoservice.dto.mapper;

import com.example.autoservice.dto.task.TaskRequestDto;
import com.example.autoservice.dto.task.TaskResponseDto;
import com.example.autoservice.model.Task;
import com.example.autoservice.service.MechanicService;
import com.example.autoservice.service.OrderService;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    private final OrderService orderService;
    private final MechanicService mechanicService;

    public TaskMapper(OrderService orderService, MechanicService mechanicService) {
        this.orderService = orderService;
        this.mechanicService = mechanicService;
    }

    public TaskResponseDto toDto(Task task) {
        TaskResponseDto dto = new TaskResponseDto();
        dto.setId(task.getId());
        dto.setType(task.getType());
        if (task.getOrder() != null) {
            dto.setOrderId(task.getOrder().getId());
        }
        if (task.getMechanic() != null) {
            dto.setMechanicId(task.getMechanic().getId());
        }
        dto.setPrice(task.getPrice());
        dto.setPaymentStatus(task.getPaymentStatus());

        return dto;
    }

    public Task toModel(TaskRequestDto requestDto) {
        Task task = new Task();
        task.setType(Task.TypeOfTask.valueOf(requestDto.getType().toUpperCase()));
        if (requestDto.getOrderId() != null) {
            task.setOrder(orderService.getById(requestDto.getOrderId()));
        }
        if (requestDto.getMechanicId() != null) {
            task.setMechanic(mechanicService.getById(requestDto.getMechanicId()));
        }
        task.setPrice(requestDto.getPrice());
        task.setPaymentStatus(Task.PaymentStatus.valueOf(requestDto.getPaymentStatus().toUpperCase()));

        return task;
    }
}
