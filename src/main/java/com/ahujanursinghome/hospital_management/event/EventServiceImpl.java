package com.ahujanursinghome.hospital_management.event;

import com.ahujanursinghome.hospital_management.config.ImageUrlBuilder;
import com.ahujanursinghome.hospital_management.department.DepartmentEntity;
import com.ahujanursinghome.hospital_management.doctors.DoctorEntity;
import com.ahujanursinghome.hospital_management.miscellaneous.SaveImageHelperMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl {
    @Value("${image.upload.dir}")
    private String uploadDir;
    @Autowired
    private EventRepo eventRepo;
    @Autowired
    private SaveImageHelperMethod imageHelperMethod;
    @Autowired
    private ImageUrlBuilder imageUrlBuilder;

    public Boolean uploadEventDetails(EventRequestBody eventRequestBody, MultipartFile file) throws IOException {
        if (eventRequestBody == null || file == null || file.isEmpty()) {
            return false;
        }
        File directory=new File(uploadDir);
    if (!directory.exists()&&!directory.mkdirs()){
        throw new IOException("Failed to creare directory:"+uploadDir);
    }
    try{
       String filepath= imageHelperMethod.saveFile("event",file);

        EventEntity eventEntity=new EventEntity();
        eventEntity.setTitle(eventRequestBody.getTitle());
        eventEntity.setDescription(eventRequestBody.getDescription());
        eventEntity.setEventDate(eventRequestBody.getEventDate());
        eventEntity.setImageUrl(filepath);

        eventRepo.save(eventEntity);
        return true;

    } catch (RuntimeException e) {
        throw new RuntimeException(e);
    }

    }

    public List<EventEntity> getAllUpcomingEvent() {
        List<EventEntity> events = eventRepo.findActiveEvents();

//        for (EventEntity event : events) {
//            if (event.getImageUrl() != null) {
//                String filename = Paths.get(event.getImageUrl()).getFileName().toString();
//                event.setImageUrl(imageUrlBuilder.buildUrl(filename));
//            }
//        }


        return events;
    }

    public Optional<EventEntity> getUpcomingEventById(Integer Id){
       Optional<EventEntity> eventEntity= eventRepo.findById(Id);
       return eventEntity;
    }
    public boolean removeEvent(Integer id) {
        Optional<EventEntity> eventOptional = eventRepo.findById(id);

        if (eventOptional.isPresent()) {
            EventEntity eventEntity = eventOptional.get();
            eventEntity.setActiveEvent(false); // Set status to inactive
            eventRepo.save(eventEntity); // Save the updated entity
            return true; // Indicate success
        }

        return false; // Indicate not found
    }



}
