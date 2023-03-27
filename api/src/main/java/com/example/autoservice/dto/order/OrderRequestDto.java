package com.example.autoservice.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    private Long carId;
    private String description;
    private List<Long> taskIds;
    private List<Long> productsIds;
    private LocalDate dateFinished;
    private String status;
}
