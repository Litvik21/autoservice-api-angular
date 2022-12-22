package com.example.autoservice.dto.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import com.example.autoservice.model.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

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
    private Order.Status status;
    private BigDecimal totalPrice;
    private LocalDate dateFinished;
}
