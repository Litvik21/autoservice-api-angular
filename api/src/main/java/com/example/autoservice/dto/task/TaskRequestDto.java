package com.example.autoservice.dto.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDto {
    private String type;
    private Long orderId;
    private Long mechanicId;
    private BigDecimal price;
    private String paymentStatus;
}
