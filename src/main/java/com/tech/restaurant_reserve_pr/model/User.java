package com.tech.restaurant_reserve_pr.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email
    @Size(max = 100)
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotBlank
    @Size(min = 8, max = 120)
    @Column(name = "password", nullable = false)
    private String password;

    private String name;
    private String phone;
    private Boolean isActive;
    private LocalDateTime creationDate;
    private LocalDateTime lastAccess;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        creationDate = LocalDateTime.now();
        isActive = true;
    }
}