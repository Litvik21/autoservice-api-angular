package com.example.autoservice.model;

import java.util.List;
import javax.persistence.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "masters")
public class Mechanic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private String lastName;
    @Enumerated(value = EnumType.STRING)
    private Status status;

    public enum Status {
        BUSY("Busy"),
        FREE("Free");
        private String value;

        Status(String value) {
            this.value = value;
        }
    }
}
