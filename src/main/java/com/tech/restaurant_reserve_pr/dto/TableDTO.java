package com.tech.restaurant_reserve_pr.dto;

import com.tech.restaurant_reserve_pr.model.TableLocation;
import com.tech.restaurant_reserve_pr.model.TableStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TableDTO {
    private Long id;

    @NotNull(message = "Table number is required")
    private Integer number;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be greater than 0")
    private Integer capacity;

    private Boolean isActive;
    private String location;
    private String status;
}
