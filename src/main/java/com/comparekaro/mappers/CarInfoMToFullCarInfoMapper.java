package com.comparekaro.mappers;

import com.comparekaro.models.domain.FullCarInfo;
import com.comparekaro.dto.SpecInfo;
import com.comparekaro.dto.document.CarInfoMDto;
import com.comparekaro.dto.document.PriceDetailsMDto;
import com.comparekaro.dto.document.SpecInfoMDto;
import com.comparekaro.models.response.Image;
import com.comparekaro.models.response.PriceOverview;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarInfoMToFullCarInfoMapper {
    public FullCarInfo map(CarInfoMDto dto){
        return FullCarInfo.builder()
                .carId(dto.getCarId())
                .year(dto.getYear())
                .name(dto.getName())
                .maxPriceOverview(mapToPriceOverview(dto.getMaxPriceDetails()))
                .minPriceOverview(mapToPriceOverview(dto.getMinPriceDetails()))
                .images(mapToImage(dto.getImages()))
                .company(dto.getCompany())
                .modelName(dto.getModelName())
                .modelId(dto.getModelId())
                .specifications(mapToSpec(dto.getSpecifications()))
                .features(mapToSpec(dto.getFeatures()))
                .build();
    }

    private List<SpecInfo> mapToSpec(List<SpecInfoMDto> specifications) {
        return specifications.stream()
                .map(spec -> new SpecInfo(spec.getName(), spec.getValue(), spec.getUnit()))
                .collect(Collectors.toList());
    }

    private Image mapToImage(List<String> images) {
        return Image.builder().urls(images).build();
    }

    private PriceOverview mapToPriceOverview(PriceDetailsMDto priceDetails) {
        return PriceOverview.builder()
                .price(Integer.parseInt(priceDetails.getPrice()))
                .formattedPrice(priceDetails.getFormattedPrice())
                .build();
    }
}
