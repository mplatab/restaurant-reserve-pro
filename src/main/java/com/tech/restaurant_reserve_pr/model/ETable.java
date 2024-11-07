package com.tech.restaurant_reserve_pr.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tables")
@Cacheable
public class ETable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private Integer number;

    @NotNull
    @Min(1)
    private Integer capacity;

    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    private TableLocation location;

    @Enumerated(EnumType.STRING)
    private TableStatus status;

    @PrePersist
    protected  void onCreate() {
        if (isActive == null) isActive = true;
        if (status == null) status = TableStatus.AVAILABLE;
    }
}
