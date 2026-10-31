package com.ahujanursinghome.hospital_management.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import com.ahujanursinghome.hospital_management.config.ImageUrlBuilder;
import com.ahujanursinghome.hospital_management.miscellaneous.SaveImageHelperMethod;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {
    @Mock
    private EventRepo eventRepo;
    @Mock
    private SaveImageHelperMethod saveImageHelperMethod;
    @Mock
    private ImageUrlBuilder imageUrlBuilder;
    @InjectMocks
    private EventServiceImpl eventServiceImpl;
    @TempDir
    Path TempDir;

    
        @Test 
        void uploadEvent_NullRequest() throws Exception{
            boolean upload=eventServiceImpl.uploadEventDetails(null, null);
            assertEquals(false, upload);
            verifyNoInteractions(eventRepo);

        }

    @Test
    void uploadEventDetailsTest() throws Exception {
         ReflectionTestUtils.setField(eventServiceImpl, "uploadDir", TempDir.resolve("uploads").toString());
        EventRequestBody eventRequestBody = EventRequestBody.builder().title("Blood Donation Camp")
                .eventDate("18-20-2020").description("A blood donation camp for all voltuntary and nonvoluntary")
                .build();
                ArgumentCaptor<EventEntity>event=ArgumentCaptor.forClass(EventEntity.class);
        MockMultipartFile mockFile =  new MockMultipartFile("file","event.jpg","image/jpeg", "dummy Image".getBytes());
        boolean uploaded = eventServiceImpl.uploadEventDetails(eventRequestBody, mockFile);
        EventEntity eventEntity = EventEntity.builder().title("Blood Donation Camp").eventDate("18-20-2020")
                .description("A blood donation camp for all voltuntary and nonvoluntary").imageUrl("abx/uploaded-image")
                .build();
        verify(eventRepo).save(event.capture());
        assertEquals(true, uploaded);
        // verify(eventRepo).save(any(EventEntity.class));
    }

}
