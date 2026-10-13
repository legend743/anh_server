package com.ahujanursinghome.hospital_management.authentication;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "userdetails")
@Getter
@Setter
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String email;
    private String password;
    private boolean enabled=true;
    private String otp;
    private LocalDateTime expiry;

}
