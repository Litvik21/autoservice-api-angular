package com.example.autoservice.controller;

import java.util.Collections;
import com.example.autoservice.dto.car.CarRequestDto;
import com.example.autoservice.model.Car;
import com.example.autoservice.model.CarOwner;
import com.example.autoservice.service.CarOwnerService;
import com.example.autoservice.service.CarService;
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
class CarControllerTest {
    @MockBean
    private CarService carService;

    @MockBean
    private CarOwnerService ownerService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldSaveCar() {
        CarOwner owner = new CarOwner(2L, Collections.emptyList(), Collections.emptyList());
        Car car = new Car(null,"audi", "a7",
                "2018", "12312",  owner);
        Mockito.when(ownerService.getById(2L)).thenReturn(owner);
        Mockito.when(carService.save(car)).thenReturn(new Car(103L,"audi",
                "a7", "2018", "12312",  owner));

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new CarRequestDto(car.getBrand(), car.getModel(),
                        car.getYear(), car.getNumber(), car.getOwner().getId()))
                .when()
                .post("/cars")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(103))
                .body("number", Matchers.equalTo("12312"))
                .body("year", Matchers.equalTo("2018"))
                .body("brand", Matchers.equalTo("audi"))
                .body("model", Matchers.equalTo("a7"));
    }
}