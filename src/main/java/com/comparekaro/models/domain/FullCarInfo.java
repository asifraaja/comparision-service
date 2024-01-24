package com.comparekaro.models.domain;

import com.comparekaro.dto.SpecInfo;
import com.comparekaro.models.response.BasicCarInfo;
import com.comparekaro.models.response.Image;
import com.comparekaro.models.response.PriceOverview;
import com.comparekaro.models.response.Variant;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FullCarInfo {
    private String carId;
    private String name;
    private String modelId;
    private String modelName;
    private String company;
    private int year;
    private List<Variant> variantList;
    private Image images;
    private PriceOverview minPriceOverview;
    private PriceOverview maxPriceOverview;
    private List<SpecInfo> specifications;
    private List<SpecInfo> features;

    public BasicCarInfo toBasicCarInfo() {
        return BasicCarInfo.builder()
                .id(carId)
                .name(name)
                .modelName(modelName)
                .modelId(modelId)
                .company(company)
                .year(year)
                .variantList(variantList)
                .images(images)
                .minPriceOverview(minPriceOverview)
                .maxPriceOverview(maxPriceOverview)
                .build();
    }
}
