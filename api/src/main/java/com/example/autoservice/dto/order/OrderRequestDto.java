package com.example.autoservice.dto.order;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

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
}
