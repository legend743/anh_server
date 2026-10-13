package com.ahujanursinghome.hospital_management.miscellaneous;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "listofaddresses")
@Data
@Getter
@Setter
public class ListOfAddresses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
