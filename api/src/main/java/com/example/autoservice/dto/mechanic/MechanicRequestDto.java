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
public class MechanicRequestDto {
    private String name;
    private List<Long> finishedOrdersId;
}
