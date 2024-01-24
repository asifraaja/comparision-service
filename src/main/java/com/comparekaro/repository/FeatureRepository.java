package com.comparekaro.repository;

import com.comparekaro.dto.FeatureDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureRepository extends JpaRepository<FeatureDto, String> {
    FeatureDto findByCarId(String carId);
}
