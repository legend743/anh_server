package com.ahujanursinghome.hospital_management.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("/event")
public class EventController {
    @Autowired
    private EventServiceImpl eventService;
    @Autowired
    private EventRepo eventRepo;
    @PostMapping(value = "/upload-upcoming-event",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String>uploadUpcomingEvent(
            @RequestParam String title,
            @RequestParam String eventDate,
            @RequestParam String description,
            @RequestParam MultipartFile file
            ) throws IOException {
        if (title ==null || eventDate==null||description==null){
            return ResponseEntity.badRequest().body("Either title,event date or description is null!!");
        }
        if (!file.isEmpty()){
            EventRequestBody eventRequestBody=new EventRequestBody();
            eventRequestBody.setTitle(title);
            eventRequestBody.setEventDate(eventDate);
            eventRequestBody.setDescription(description);
       Boolean isResult= eventService.uploadEventDetails(eventRequestBody,file);
        if (!isResult){
            return ResponseEntity.badRequest().body("Event not uploaded");
        }
        return ResponseEntity.ok().body("event successfully uploaded");
        }
return ResponseEntity.status(HttpStatusCode.valueOf(500)).body("something went wrong!!");
    }

    @GetMapping("/all-upcoming-events")
    public ResponseEntity<List<EventEntity>> getAllupcomingEvent(){
      List<EventEntity>allUpcomingEvent= eventService.getAllUpcomingEvent();
      if (allUpcomingEvent.isEmpty()){
          return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok().body(allUpcomingEvent);
    }
    
    @GetMapping("/all-upcoming-event/{Id}")
    public ResponseEntity<Optional<EventEntity>>getUpcomingEvent(@RequestParam int Id){
        Optional<EventEntity> upcomingEvent=eventService.getUpcomingEventById(Id);
        if (upcomingEvent.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(upcomingEvent);
    }
    @PatchMapping("/remove/event/{id}")
    public ResponseEntity<String> removeEvent(@PathVariable Integer id) {
        boolean isRemoved = eventService.removeEvent(id);

        if (isRemoved) {
            return ResponseEntity.ok("Event marked as inactive successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found with ID: " + id);
        }
    }



}
