package com.example.autoservice.model;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;
    private String description;
    private LocalDate dateReceived;
    @OneToMany
    @JoinTable(name = "orders_jobs",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id"))
    private List<Task> tasks;
    @OneToMany
    @JoinTable(name = "orders_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;
    @Enumerated(value = EnumType.STRING)
    private Status status;
    private BigDecimal totalPrice;
    private LocalDate dateFinished;


    public enum Status {
        RECEIVED("Received"),
        PROCESS("Process"),
        SUCCESSFULLY_COMPLETED("Successfully_Completed"),
        NOT_SUCCESSFULLY_COMPLETED("Not_Successfully_Completed"),
        PAID("Paid");
        private String value;

        Status(String value) {
            this.value = value;
        }
    }
}
