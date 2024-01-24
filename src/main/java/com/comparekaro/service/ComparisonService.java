package com.comparekaro.service;

import com.comparekaro.errors.CarNotFoundException;
import com.comparekaro.errors.CarsNotFoundException;
import com.comparekaro.executor.CarExecutorThreadPool;
import com.comparekaro.helper.ComparisonDetailsResponseCreator;
import com.comparekaro.models.request.CompareCarRequest;
import com.comparekaro.models.response.ComparisionDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.comparekaro.models.domain.FullCarInfo;

@Service
@Slf4j
public class ComparisonService {
    @Autowired private CacheService cacheService;
//    @Autowired private CarExecutorThreadPool carExecutorThreadPool;

    @Autowired private ComparisonDetailsResponseCreator comparisionDetailsGenerator;

    public ComparisionDetails compareCars(CompareCarRequest request){
        List<String> requestCars = request.getCars();
        List<String> cars = requestCars.stream().filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        List<CompletableFuture<FullCarInfo>> futures = new ArrayList<>();
        Map<String, FullCarInfo> carMap = new HashMap<>();
        for(String car : cars){
//            CompletableFuture<FullCarInfo> cf
//                    = carExecutorThreadPool.supplyAsync(() -> cacheService.getCarInfo(car));
//            futures.add(cf);
            FullCarInfo carInfo = null;
            try{
                carInfo=cacheService.getCarInfo(car);
            }catch (CarNotFoundException e){
                log.info("One of the car not found : " + car);
            }
            if(carInfo != null && carInfo.getCarId() != null && !carInfo.getCarId().isEmpty())
                carMap.put(carInfo.getCarId(), carInfo);
        }

//        Map<String, FullCarInfo> carMap = futures.stream()
//                .map(CompletableFuture::join)
//                .filter(Objects::nonNull)
//                .filter(fullCarInfo -> null != fullCarInfo.getId() && fullCarInfo.getId().isEmpty())
//                .collect(Collectors.toMap(FullCarInfo::getId, Function.identity(), (f, s) -> f));
        if(carMap.isEmpty()){
            throw new CarsNotFoundException("Unable to compare car as car info not found");
        }
        List<String> actualCarList = new ArrayList<>(cars);
        for(String car : actualCarList){
            if(!carMap.containsKey(car)) cars.remove(car);
        }
        return comparisionDetailsGenerator.generator(carMap, cars, request);

    }

}
