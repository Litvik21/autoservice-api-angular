package com.example.autoservice.controller;

import com.example.autoservice.dto.mapper.TaskMapper;
import com.example.autoservice.dto.task.TaskRequestDto;
import com.example.autoservice.dto.task.TaskResponseDto;
import com.example.autoservice.model.Task;
import com.example.autoservice.service.TaskService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskMapper mapper;
    private final TaskService taskService;

    public TaskController(TaskMapper mapper, TaskService taskService) {
        this.mapper = mapper;
        this.taskService = taskService;
    }

    @PostMapping
    @ApiOperation(value = "Save a new task to DB")
    public TaskResponseDto save(@RequestBody TaskRequestDto requestDto) {
        Task task = mapper.toModel(requestDto);
        return mapper.toDto(taskService.save(task));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update task by id")
    public TaskResponseDto update(
            @PathVariable @ApiParam(value = "id of task that you want to update")
            Long id,
            @RequestBody TaskRequestDto requestDto) {

        Task task = mapper.toModel(requestDto);
        task.setId(id);
        return mapper.toDto(taskService.update(task));
    }

    @PutMapping("/update-status/{id}")
    @ApiOperation(value = "Update status of task")
    public TaskResponseDto updateStatus(
            @PathVariable @ApiParam(value = "id of task that you want to update status")
            Long id,
            @RequestBody @ApiParam(value = "Updated status for task")
            String status) {

        return mapper.toDto(taskService.updateStatus(id, status));
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "Get task by id")
    public TaskResponseDto getTask(
            @PathVariable @ApiParam(value = "id of task that you want to get")
            Long id) {

        return mapper.toDto(taskService.getById(id));
    }

    @GetMapping
    @ApiOperation(value = "Get list of tasks")
    public List<TaskResponseDto> getAll() {
        return taskService.getAll().stream()
                .map(mapper::toDto)
                .toList();
    }
}
