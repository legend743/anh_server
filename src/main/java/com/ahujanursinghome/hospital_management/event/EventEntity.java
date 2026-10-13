package com.ahujanursinghome.hospital_management.event;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "event")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String title;
    private String eventDate;
    private String description;
    private String imageUrl;
    private boolean activeEvent=true;

}
