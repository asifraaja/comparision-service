package com.comparekaro.helper;

import com.comparekaro.models.domain.SuggestionModel;
import com.comparekaro.models.response.BasicCarInfo;
import com.comparekaro.models.response.suggestion.SuggestionResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SuggestionResponseCreator {

    public static final ArrayList<BasicCarInfo> EMPTY_ARRAYLIST = new ArrayList<>();

    public SuggestionResponse createResp(SuggestionModel dto){
        if(dto == null) return SuggestionResponse.builder().build();
        return SuggestionResponse.builder()
                .basicCarInfo(BasicCarInfo.builder()
                        .id(dto.getId())
                        .name(dto.getName())
                        .company(dto.getCompany())
                        .modelName(dto.getModelName())
                        .build())
                .similarCars(mapSimilarCars(dto.getSimilarCars()))
                .pageNum(dto.getPageNum())
                .endIndex(dto.getEndIndex())
                .startIndex(dto.getStartIndex())
                .totalCars(dto.getTotalCars())
                .build();

    }

    private List<BasicCarInfo> mapSimilarCars(List<SuggestionModel> similarCars) {
        if(null == similarCars) return EMPTY_ARRAYLIST;
        return similarCars.stream()
                .map(SuggestionResponseCreator::mapSuggestDtoToCarInfo)
                .collect(Collectors.toList());
    }

    private static BasicCarInfo mapSuggestDtoToCarInfo(SuggestionModel car) {
        if(null == car) return BasicCarInfo.builder().build();
        return BasicCarInfo.builder()
                .id(car.getId())
                .name(car.getName())
                .company(car.getCompany())
                .modelName(car.getModelName())
                .build();
    }
}
