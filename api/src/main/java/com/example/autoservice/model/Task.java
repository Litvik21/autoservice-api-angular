package com.example.autoservice.model;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.*;

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
        OIL_CHANGE("OilChange"),
        ENGINE_REPAIR("EnginRepair"),
        GRM_CHANGE("GRMChange");

        private String value;
        TypeOfTask(String value) {
            this.value = value;
        }
    }

    public enum PaymentStatus {
        PAID("Paid"),
        NOT_PAID("NotPaid");
        private String value;

        PaymentStatus(String value) {
            this.value = value;
        }
    }
}
