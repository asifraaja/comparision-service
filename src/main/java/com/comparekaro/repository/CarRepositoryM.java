package com.comparekaro.repository;

import com.comparekaro.dto.document.CarInfoMDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepositoryM extends MongoRepository<CarInfoMDto, String> {
    Optional<CarInfoMDto> findByCarId(String carId);
}
