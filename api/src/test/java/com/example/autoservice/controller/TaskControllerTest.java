package com.example.autoservice.controller;

import java.math.BigDecimal;
import com.example.autoservice.dto.task.TaskRequestDto;
import com.example.autoservice.model.Mechanic;
import com.example.autoservice.model.Order;
import com.example.autoservice.model.Task;
import com.example.autoservice.service.MechanicService;
import com.example.autoservice.service.OrderService;
import com.example.autoservice.service.TaskService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {
    @MockBean
    private TaskService taskService;

    @MockBean
    private OrderService orderService;

    @MockBean
    private MechanicService mechanicService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldSaveTask() {
        Task task = new Task();
        task.setPrice(BigDecimal.valueOf(300));
        Mockito.when(mechanicService.getById(52L)).thenReturn(new Mechanic());
        Mockito.when(orderService.getById(15L)).thenReturn(new Order());
        Mockito.when(taskService.save(task))
                .thenReturn(new Task(4L,null, new Order(), new Mechanic(), task.getPrice(), null));

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new TaskRequestDto(null, 15L, 52L, task.getPrice(), null))
                .when()
                .post("tasks")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(4))
                .body("price", Matchers.equalTo(300));
    }
}