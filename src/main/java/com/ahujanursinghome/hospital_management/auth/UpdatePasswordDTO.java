package com.ahujanursinghome.hospital_management.auth;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordDTO {
    private String email;
    private String password;

}
