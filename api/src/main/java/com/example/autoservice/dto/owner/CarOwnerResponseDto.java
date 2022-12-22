package com.example.autoservice.dto.owner;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarOwnerResponseDto {
    private Long id;
    private List<Long> carsId;
    private List<Long> ordersId;
}
