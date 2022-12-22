package com.example.autoservice.controller;

import java.util.Collections;
import java.util.List;
import com.example.autoservice.dto.owner.CarOwnerRequestDto;
import com.example.autoservice.model.Car;
import com.example.autoservice.model.CarOwner;
import com.example.autoservice.model.Order;
import com.example.autoservice.service.CarOwnerService;
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
class CarOwnerControllerTest {
    @MockBean
    private CarOwnerService ownerService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldSaveCarOwner() {
        CarOwner owner = new CarOwner();
        Mockito.when(ownerService.save(owner))
                .thenReturn(new CarOwner(3L, Collections.emptyList(), Collections.emptyList()));

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new CarOwnerRequestDto(Collections.emptyList(), Collections.emptyList()))
                .when()
                .post("/car-owners")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(3));
    }

    @Test
    void shouldGetAllOrderOfOwner() {
        Order order = new Order();
        order.setId(2L);
        order.setCar(new Car());
        order.setProducts(Collections.emptyList());
        order.setTasks(Collections.emptyList());
        Mockito.when(ownerService.findAllOrdersById(3L))
                .thenReturn(List.of(order));

        RestAssuredMockMvc.given()
                .when()
                .get("/car-owners/orders/3")
                .then()
                .body("size()", Matchers.equalTo(1))
                .body("[0].id", Matchers.equalTo(2));
    }
}