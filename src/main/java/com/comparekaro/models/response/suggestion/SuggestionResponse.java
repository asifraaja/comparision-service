package com.comparekaro.models.response.suggestion;

import com.comparekaro.models.response.BasicCarInfo;
import com.comparekaro.models.response.Response;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class SuggestionResponse{
    @JsonProperty("errors")
    List<RespError> errors;

    @JsonProperty("basicCarInfo")
    BasicCarInfo basicCarInfo;

    @JsonProperty("similarCars")
    List<BasicCarInfo> similarCars;

    @JsonProperty("totalCars")
    int totalCars;

    @JsonProperty("pageNum")
    int pageNum;

    @JsonProperty("startIndex")
    int startIndex;

    @JsonProperty("endIndex")
    int endIndex;
}
