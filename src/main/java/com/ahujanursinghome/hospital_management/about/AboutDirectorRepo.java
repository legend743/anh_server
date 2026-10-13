package com.ahujanursinghome.hospital_management.about;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutDirectorRepo extends JpaRepository<AboutDirectorEntity,Long> {
    @Query(value = "Select * From directordetails  order by id desc  limit 1",nativeQuery = true)
    AboutDirectorEntity findPresentDirectorDetails();
}
