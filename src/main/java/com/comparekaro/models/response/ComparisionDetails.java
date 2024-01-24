package com.comparekaro.models.response;

import com.comparekaro.models.response.suggestion.RespError;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"errors", "metadata", "carsCompared", "images", "colors", "basicCarInfo", "minPriceOverview", "maxPriceOverview"
        , "similarity", "difference"})
@Getter
public class ComparisionDetails {
    @JsonProperty("errors")
    List<RespError> errors;
    @JsonProperty("carsCompared")
    List<String> cars;
    @JsonProperty("images")
    List<Image> images;
    @JsonProperty("colors")
    List<String> colors;
    @JsonProperty("basicCarInfo")
    List<BasicCarInfo> basicCarInfoList;
    @JsonProperty("minPriceOverview")
    List<PriceOverview> minPriceOverviewList;
    @JsonProperty("maxPriceOverview")
    List<PriceOverview> maxPriceOverviewList;
    @JsonProperty("specifications")
    List<SpecDetailsResponse> specificationList;
    @JsonProperty("features")
    List<SpecDetailsResponse> featureList;
    @JsonProperty("similarity")
    @Setter List<SpecDetailsResponse> similarity;
    @JsonProperty("difference")
    @Setter List<SpecDetailsResponse> differences;
    @JsonProperty("variants")
    List<Variant> variantList;
    @JsonProperty("metadata")
    Metadata metadata;
}
