package com.comparekaro.mappers;

import com.comparekaro.dto.Car;
import com.comparekaro.dto.FeatureDto;
import com.comparekaro.models.domain.FullCarInfo;
import com.comparekaro.dto.SpecInfo;
import com.comparekaro.dto.SpecificationDto;
import com.comparekaro.models.response.Image;
import com.comparekaro.models.response.PriceOverview;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarToFullCarInfoMapper {
    public FullCarInfo map(Car car,
                                         FeatureDto feature,
                                         SpecificationDto specification) {
        return FullCarInfo.builder()
                .carId(car.getId())
                .modelId(car.getModelId())
                .name(car.getName())
                .company(car.getCompany())
                .year(car.getYear())
                .modelName(car.getModelName())
                .images(setImages(car))
                .minPriceOverview(setMinPrice(car))
                .maxPriceOverview(setMaxPrice(car))
                .features(setFeatures(feature))
                .specifications(setSpecs(specification))
                .build();
    }

    private List<SpecInfo> setSpecs(SpecificationDto dto) {
        List<SpecInfo> list = Arrays.asList(
                new SpecInfo("Engine", dto.getEngine(), ""),
                new SpecInfo("Engine Type", dto.getEngineType(), ""),
                new SpecInfo("Fuel Type", dto.getFuelType(), ""),
                new SpecInfo("Front Brake Type", dto.getFrontBrakeType(), ""),
                new SpecInfo("Rear Brake Type", dto.getRearBrakeType(), ""),
                new SpecInfo("Doors", String.valueOf(dto.getDoors()), ""),
                new SpecInfo("Fuel Tank Capacity", String.valueOf(dto.getFuelTankCapacity()), "litre"),
                new SpecInfo("Mileage", String.valueOf(dto.getMileage()), "kmpl"),
                new SpecInfo("Seating Capacity", String.valueOf(dto.getSeatingCapacity()), "")
        );
        List<SpecInfo> specInfoList = new ArrayList<>(list);
        if(null != dto.getOthers()){
            specInfoList.addAll(Arrays.stream(dto.getOthers())
                    .map(s -> new SpecInfo("Other Specifications", s, ""))
                    .collect(Collectors.toList()));
        }
        return specInfoList;
    }

    private List<SpecInfo> setFeatures(FeatureDto dto) {
        List<SpecInfo> specInfoList = new ArrayList<>();
        List<SpecInfo> list = Arrays.asList(new SpecInfo("Overspeed Warning", dto.getOverspeedWarning(), ""),
                new SpecInfo("Air Conditioner", dto.getAirConditioner(), ""),
                new SpecInfo("Bluetooth", dto.getBluetooth(), ""),
                new SpecInfo("Heater", dto.getHeater(), ""),
                new SpecInfo("Internet", dto.getInternet(), ""),
                new SpecInfo("Seat Belt Warning", dto.getSeatBeltWarning(), ""),
                new SpecInfo("Hill Hold Control", dto.getHillHoldControl(), ""),
                new SpecInfo("Airbags", String.valueOf(dto.getAirbags()), ""));

        specInfoList.addAll(list);
        if(null != dto.getExtraFeatures()){
            specInfoList.addAll(
                    Arrays.stream(dto.getExtraFeatures())
                            .map(f -> new SpecInfo("Extra Features", f, ""))
                            .collect(Collectors.toList()));
        }
        return specInfoList;
    }

    private PriceOverview setMaxPrice(Car car) {
        return PriceOverview.builder()
                .price(Integer.parseInt(car.getMaxPrice()))
                .formattedPrice(car.getMaxPriceFormatted())
                .build();
    }

    private PriceOverview setMinPrice(Car car) {
        return PriceOverview.builder()
                .price(Integer.parseInt(car.getMinPrice()))
                .formattedPrice(car.getMinPriceFormatted())
                .build();
    }

    private Image setImages(Car car) {
        if(null != car.getImages()){
            return Image.builder()
                    .urls(new ArrayList<>(Arrays.asList(car.getImages())))
                    .build();
        }
        return Image.builder().build();
    }
}
