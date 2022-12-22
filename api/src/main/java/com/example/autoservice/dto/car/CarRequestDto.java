package com.example.autoservice.dto.car;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarRequestDto {
    private String brand;
    private String model;
    private String year;
    private String number;
    private Long ownerId;
}
