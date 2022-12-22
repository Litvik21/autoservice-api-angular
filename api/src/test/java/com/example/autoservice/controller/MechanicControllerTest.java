package com.example.autoservice.controller;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import com.example.autoservice.dto.mechanic.MechanicRequestDto;
import com.example.autoservice.model.Car;
import com.example.autoservice.model.Mechanic;
import com.example.autoservice.model.Order;
import com.example.autoservice.service.MechanicService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
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
import org.hamcrest.Matchers;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class MechanicControllerTest {
    @MockBean
    private MechanicService mechanicService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldSaveMechanic() {
        Mechanic mechanic = new Mechanic();
        mechanic.setName("Arnold");
        Mockito.when(mechanicService.save(mechanic))
                .thenReturn(new Mechanic(4L, mechanic.getName(), Collections.emptyList()));

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new MechanicRequestDto(mechanic.getName(), Collections.emptyList()))
                .when()
                .post("/mechanics")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(4))
                .body("name", Matchers.equalTo("Arnold"));
    }

    @Test
    void shouldGetFinishedOrders() {
        Order order = new Order();
        order.setId(47L);
        order.setCar(new Car());
        order.setProducts(Collections.emptyList());
        order.setTasks(Collections.emptyList());
        Order order1 = new Order();
        order1.setId(5L);
        order1.setCar(new Car());
        order1.setProducts(Collections.emptyList());
        order1.setTasks(Collections.emptyList());
        Mockito.when(mechanicService.getOrders(13L))
                .thenReturn(List.of(order, order1));

        RestAssuredMockMvc.given()
                .when()
                .get("/mechanics/13/finished-orders")
                .then()
                .body("size()", Matchers.equalTo(2))
                .body("[0].id", Matchers.equalTo(47))
                .body("[1].id", Matchers.equalTo(5));
    }

    @Test
    void shouldGetSalaryOfMechanic() {
        Mockito.when(mechanicService.getSalary(33L))
                .thenReturn(BigDecimal.valueOf(12145.96));

        RestAssuredMockMvc.given()
                .when()
                .get("/mechanics/33")
                .then()
                .statusCode(200)
                .body(Matchers.equalTo("12145.96"));
    }
}