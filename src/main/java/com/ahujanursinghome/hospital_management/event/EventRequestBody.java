package com.ahujanursinghome.hospital_management.event;


import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestBody {

    private String title;
    private String eventDate;
    private String description;
}
