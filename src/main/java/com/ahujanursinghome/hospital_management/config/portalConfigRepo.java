package com.ahujanursinghome.hospital_management.config;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface portalConfigRepo extends JpaRepository<PortalConfigurationEntity,Integer> {
    PortalConfigurationEntity findFirstByOrderByIdDesc();
}
