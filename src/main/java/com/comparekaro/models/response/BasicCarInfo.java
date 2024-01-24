package com.comparekaro.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class BasicCarInfo {
    @JsonProperty("carId")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("modelId")
    private String modelId;
    @JsonProperty("modelName")
    private String modelName;
    @JsonProperty("company")
    private String company;
    @JsonProperty("year")
    private int year;
    @JsonProperty("variants")
    List<Variant> variantList;
    @JsonProperty("images")
    Image images;
    @JsonProperty("minPriceDetails")
    PriceOverview minPriceOverview;
    @JsonProperty("maxPriceDetails")
    PriceOverview maxPriceOverview;
}
