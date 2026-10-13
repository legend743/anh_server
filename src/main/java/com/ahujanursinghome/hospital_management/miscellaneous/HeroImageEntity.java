package com.ahujanursinghome.hospital_management.miscellaneous;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "heroimage")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HeroImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imagepath;

}
