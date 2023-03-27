package com.example.autoservice.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private TypeOfTask type;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @OneToOne
    @JoinColumn(name = "mechanic_id")
    private Mechanic mechanic;
    private BigDecimal price;
    @Enumerated(value = EnumType.STRING)
    private PaymentStatus paymentStatus;

    public enum TypeOfTask {
        DIAGNOSTICS("Diagnostics"),
        OIL_CHANGE("Oil_Change"),
        ENGINE_REPAIR("Engine_Repair"),
        GRM_CHANGE("GRM_Change");

        private String value;

        TypeOfTask(String value) {
            this.value = value;
        }
    }

    public enum PaymentStatus {
        PAID("Paid"),
        NOT_PAID("Not_Paid");
        private String value;

        PaymentStatus(String value) {
            this.value = value;
        }
    }
}
