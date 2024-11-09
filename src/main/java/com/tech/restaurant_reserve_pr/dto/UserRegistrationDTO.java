package com.tech.restaurant_reserve_pr.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDTO {
    private String email;
    private String password;
    private String name;
    private String phone;
}
