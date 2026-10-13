package com.ahujanursinghome.hospital_management.miscellaneous;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Entity
@Data
@Getter
@Setter
@Table(name = "contact")
public class ContactUsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    private String Address;
    private String contact_no;
    private String email;

}
