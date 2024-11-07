package com.tech.restaurant_reserve_pr.dto;

import com.tech.restaurant_reserve_pr.model.TableLocation;
import com.tech.restaurant_reserve_pr.model.TableStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class TableUpdateDTO {
    private Integer capacity;
    private Boolean isActive;
    private String location;
    private String status;
}
