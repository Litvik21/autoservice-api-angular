package com.example.autoservice.dto.mechanic;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MechanicResponseDto {
    private Long id;
    private String name;
    private List<Long> finishedOrdersId;
}
