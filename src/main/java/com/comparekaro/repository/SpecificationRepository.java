package com.comparekaro.repository;

import com.comparekaro.dto.SpecificationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecificationRepository extends JpaRepository<SpecificationDto, String> {
    SpecificationDto findByCarId(String carId);
}
