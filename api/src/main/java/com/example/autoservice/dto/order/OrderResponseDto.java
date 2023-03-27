package com.example.autoservice.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private Long id;
    private Long carId;
    private String description;
    private LocalDate dateReceived;
    private List<Long> taskIds;
    private List<Long> productsIds;
    private String status;
    private BigDecimal totalPrice;
    private LocalDate dateFinished;
}
