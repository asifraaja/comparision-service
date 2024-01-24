package com.comparekaro.helper;

import com.comparekaro.models.domain.SuggestionModel;
import com.comparekaro.models.response.BasicCarInfo;
import com.comparekaro.models.response.suggestion.SuggestionResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SuggestionResponseCreator {
    public SuggestionResponse createResp(SuggestionModel dto){
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
        return similarCars.stream()
                .map(SuggestionResponseCreator::mapSuggestDtoToCarInfo)
                .collect(Collectors.toList());
    }

    private static BasicCarInfo mapSuggestDtoToCarInfo(SuggestionModel car) {
        return BasicCarInfo.builder()
                .id(car.getId())
                .name(car.getName())
                .company(car.getCompany())
                .modelName(car.getModelName())
                .build();
    }
}
