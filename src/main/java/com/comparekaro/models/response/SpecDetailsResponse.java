package com.comparekaro.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class SpecDetailsResponse {
    @JsonProperty("group")
    String group;
    @JsonProperty("groupOrder")
    int groupOrder;
    @JsonProperty("items")
    List<Specification> items;
}
