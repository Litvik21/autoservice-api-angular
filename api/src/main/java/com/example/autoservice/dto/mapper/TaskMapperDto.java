package com.example.autoservice.dto.mapper;

import com.example.autoservice.dto.task.TaskRequestDto;
import com.example.autoservice.dto.task.TaskResponseDto;
import com.example.autoservice.model.Task;
import com.example.autoservice.service.MechanicService;
import com.example.autoservice.service.OrderService;
import org.springframework.stereotype.Component;

@Component
public class TaskMapperDto {
    private final OrderService orderService;
    private final MechanicService mechanicService;

    public TaskMapperDto(OrderService orderService, MechanicService mechanicService) {
        this.orderService = orderService;
        this.mechanicService = mechanicService;
    }

    public TaskResponseDto toDto(Task task) {
        TaskResponseDto dto = new TaskResponseDto();
        dto.setId(task.getId());
        dto.setType(task.getType());
        dto.setOrderId(task.getOrder().getId());
        dto.setMechanicId(task.getMechanic().getId());
        dto.setPrice(task.getPrice());
        dto.setPaymentStatus(task.getPaymentStatus());

        return dto;
    }

    public Task toModel(TaskRequestDto requestDto) {
        Task task = new Task();
        task.setType(requestDto.getType());
        task.setOrder(orderService.getById(requestDto.getOrderId()));
        task.setMechanic(mechanicService.getById(requestDto.getMechanicId()));
        task.setPrice(requestDto.getPrice());
        task.setPaymentStatus(requestDto.getPaymentStatus());

        return task;
    }
}
