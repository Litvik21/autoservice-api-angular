package com.example.autoservice.controller;

import java.math.BigDecimal;
import java.util.Collections;
import com.example.autoservice.dto.order.OrderRequestDto;
import com.example.autoservice.model.Car;
import com.example.autoservice.model.Order;
import com.example.autoservice.service.CarService;
import com.example.autoservice.service.OrderService;
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
class OrderControllerTest {
    @MockBean
    private OrderService orderService;

    @MockBean
    private CarService carService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldSaveOrder() {
        Order order = new Order();
        order.setDescription("oil change");
        order.setTotalPrice(BigDecimal.valueOf(5123));
        Mockito.when(carService.getById(22L)).thenReturn(new Car());
        Mockito.when(orderService.save(order))
                .thenReturn(new Order(3L, new Car(), order.getDescription(),
                        null, Collections.emptyList(), Collections.emptyList(),
                        null, null, null));

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new OrderRequestDto(22L, order.getDescription(),
                        Collections.emptyList(), Collections.emptyList(), null))
                .when()
                .post("/orders")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(3))
                .body("description", Matchers.equalTo("oil change"));
    }

    @Test
    void shouldGetTotalPriceOfOrder() {
        Mockito.when(orderService.getPrice(31L))
                .thenReturn(BigDecimal.valueOf(10441));

        RestAssuredMockMvc.given()
                .when()
                .get("/orders/price/31")
                .then()
                .statusCode(200)
                .body(Matchers.equalTo("10441"));
    }
}