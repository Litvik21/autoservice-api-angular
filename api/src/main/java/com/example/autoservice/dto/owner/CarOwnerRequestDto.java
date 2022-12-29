package com.example.autoservice.dto.owner;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarOwnerRequestDto {
    private String name;
    private List<Long> carsId;
    private List<Long> ordersId;
}
