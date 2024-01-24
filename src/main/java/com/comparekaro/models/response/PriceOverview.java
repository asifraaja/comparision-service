package com.comparekaro.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PriceOverview {
    @JsonProperty("price")
    int price;
    @JsonProperty("color")
    String color;
    @JsonProperty("formattedPrice")
    String formattedPrice;
}
