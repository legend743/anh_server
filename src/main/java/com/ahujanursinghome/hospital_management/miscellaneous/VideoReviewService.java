package com.ahujanursinghome.hospital_management.miscellaneous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoReviewService {
    @Autowired
    private  VideoReviewRepo videoReviewRepo;
    public String uploadImageLink(VideoReviewEntity videoReviewEntity){
        if (videoReviewEntity==null){
            return "please pass expected arguments!!";
        }
        try {
            videoReviewRepo.save(videoReviewEntity);
            return "upload successfully!!";
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public ResponseEntity<List<VideoReviewEntity>> getAllVideoReview(){

        List<VideoReviewEntity>allReviewVideos=videoReviewRepo.findAll();
        if (allReviewVideos.isEmpty()){
            return ResponseEntity.noContent().build();
        }try {
            return ResponseEntity.ok(allReviewVideos);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public String deleteReviewVideo(Long id){
        if (!videoReviewRepo.existsById(id)){
            return "Video is not available!";
        }
        try {
            videoReviewRepo.deleteById(id);
            return "video deleted successfully!";
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
