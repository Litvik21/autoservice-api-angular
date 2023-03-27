package com.example.autoservice.service;

import com.example.autoservice.model.Mechanic;
import com.example.autoservice.model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;


class MechanicServiceImplTest {
    @InjectMocks
    private MechanicServiceImpl mechanicService;
    @Mock
    private TaskServiceImpl taskService;

    private List<Task> tasks;

    @BeforeEach
    void setUp() {
        Mechanic mechanic = new Mechanic(2L, "Vlad", Collections.emptyList());
        Task task1 = new Task(2L, null, null, mechanic,
                BigDecimal.valueOf(155), Task.PaymentStatus.NOT_PAID);
        Task task2 = new Task(3L, null, null, mechanic,
                BigDecimal.valueOf(200), Task.PaymentStatus.NOT_PAID);
        Task task3 = new Task(5L,null, null, mechanic,
                BigDecimal.valueOf(375), Task.PaymentStatus.NOT_PAID);
        tasks = List.of(task1, task2, task3);
    }

    @Test
    void shouldReturnMechanicSalary() {
        Mockito.when(taskService.findTasksByMechanicId(3L)).thenReturn(tasks);
        BigDecimal actual = mechanicService.getSalary(3L);
        BigDecimal expected = BigDecimal.valueOf(292.0);
        Assertions.assertEquals(expected, actual);
    }
}