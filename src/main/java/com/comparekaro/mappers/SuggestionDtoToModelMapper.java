package com.comparekaro.mappers;

import com.comparekaro.dto.SuggestionDto;
import com.comparekaro.dto.udt.ItemUDT;
import com.comparekaro.models.domain.SuggestionModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SuggestionDtoToModelMapper {

    public static final ArrayList<SuggestionModel> EMPTY_SUGGESSTION = new ArrayList<>();

    public SuggestionModel map(SuggestionDto dto){
        if(dto == null) return SuggestionModel.builder().build();
        return SuggestionModel.builder()
                .id(dto.getId())
                .modelName(dto.getModelName())
                .name(dto.getName())
                .company(dto.getCompany())
                .similarCars(mapSimilarCars(dto.getSimilarCars()))
                .build();
    }

    private List<SuggestionModel> mapSimilarCars(List<ItemUDT> similarCars) {
        if(similarCars == null) return EMPTY_SUGGESSTION;
        return similarCars.stream()
                .map(itemUdt -> SuggestionModel.builder()
                        .id(itemUdt.getId())
                        .modelName(itemUdt.getModelName())
                        .name(itemUdt.getName())
                        .company(itemUdt.getCompany())
                        .build())
                .collect(Collectors.toList());

    }
}
