package com.example.autoservice.dto.task;

import java.math.BigDecimal;

import com.example.autoservice.model.Task;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDto {
    private Task.TypeOfTask type;
    private Long orderId;
    private Long mechanicId;
    private BigDecimal price;
    private Task.PaymentStatus paymentStatus;
}
