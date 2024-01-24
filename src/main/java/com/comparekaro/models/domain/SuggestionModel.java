package com.comparekaro.models.domain;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class SuggestionModel {
    private String id;
    private String name;
    private String modelName;
    private List<SuggestionModel> similarCars;
    private String company;
    private int totalCars;
    private int pageNum;
    private int startIndex;
    private int endIndex;

}
