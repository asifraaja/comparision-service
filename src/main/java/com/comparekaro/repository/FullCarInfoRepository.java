package com.comparekaro.repository;

import com.comparekaro.models.domain.FullCarInfo;
import com.comparekaro.dto.document.CarInfoMDto;
import com.comparekaro.mappers.CarInfoMToFullCarInfoMapper;
import com.comparekaro.mappers.CarToFullCarInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FullCarInfoRepository {
    @Autowired
    private CarRepository carRepository;
    @Autowired private CarRepositoryM carRepositoryM;
    @Autowired private FeatureRepository featureRepository;
    @Autowired private SpecificationRepository specificationRepository;
    @Autowired private CarToFullCarInfoMapper carToFullCarInfoMapper;
    @Autowired private CarInfoMToFullCarInfoMapper carInfoMToFullCarInfoMapper;

    public FullCarInfo findByCarId(String carId) {
        Optional<CarInfoMDto> carOpt = carRepositoryM.findByCarId(carId);
        CarInfoMDto car = carOpt.get();
        return carInfoMToFullCarInfoMapper.map(car);
    }


}
