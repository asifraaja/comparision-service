package com.comparekaro.service;

import com.comparekaro.dto.SuggestionDto;
import com.comparekaro.errors.CarNotFoundException;
import com.comparekaro.helper.SuggestionResponseCreator;
import com.comparekaro.mappers.SuggestionDtoToModelMapper;
import com.comparekaro.models.domain.SuggestionModel;
import com.comparekaro.models.request.SuggestCarRequest;
import com.comparekaro.models.response.suggestion.SuggestionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SuggestionService {
    @Autowired
    private CacheService cacheService;
    @Autowired private SuggestionDtoToModelMapper suggestionDtoToModelMapper;

    public SuggestionModel suggest(SuggestCarRequest request){
        SuggestionDto suggestDto = cacheService.getSuggestions(request.getCarId());

        SuggestionModel suggestionModel = suggestionDtoToModelMapper.map(suggestDto);

        paginateIt(suggestionModel, request.getPageNum(), request.getPageSize());

        return suggestionModel;
    }

    private void paginateIt(SuggestionModel model,
                            int pageNum,
                            int pageSize){
        List<SuggestionModel> similarCars = model.getSimilarCars();
        int end = pageNum * pageSize, st = end - pageSize;
        int size = similarCars.size();
        List<SuggestionModel> paginatedCars;
        try{
            end = Math.min(end, size);
            paginatedCars = similarCars.subList(st, end);
        }catch (Exception e){
            st = 0;
            end = Math.min(size, pageSize);
            paginatedCars = similarCars.subList(0, Math.min(size, pageSize));
        }

        model.setSimilarCars(Collections.unmodifiableList(paginatedCars));
        model.setTotalCars(size);
        model.setPageNum(pageNum);
        model.setStartIndex(st);
        model.setEndIndex(end);
    }
}
