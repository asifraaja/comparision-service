package com.comparekaro.helper;

import com.comparekaro.errors.ValidationException;
import com.comparekaro.models.request.CompareCarRequest;
import com.comparekaro.models.request.SuggestCarRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RequestValidator {

    public static final String DEFAULT_PAGE_NUM = "1";
    public static final String DEFAULT_PAGE_SIZE = "10";

    public SuggestCarRequest validateSuggestionRequest(String carId, String pageNum, String pageSize){
        if(carId == null || carId.isEmpty())
            throw new ValidationException("carId cannot be null or empty");
        if(pageNum == null || pageNum.trim().isEmpty()) {pageNum = DEFAULT_PAGE_NUM;}
        if(pageSize == null || pageSize.trim().isEmpty()) {pageNum = DEFAULT_PAGE_SIZE;}
        int page = checkForValidPageNum(pageNum);
        int size = checkForValidPageSize(pageSize);

        return SuggestCarRequest.builder()
                .carId(carId)
                .pageNum(page)
                .pageSize(size)
                .build();
    }

    private int checkForValidPageSize(String pageSize) {
        try{
            int page = Integer.parseInt(pageSize);
            if(page < 0 || page > 100) throw new ValidationException("pageNum should be between 1 and 100 (inclusive).");
            return page;
        }catch (Exception e){
            throw new ValidationException("pageSize should be between 1 and 100 (inclusive).");
        }
    }

    private int checkForValidPageNum(String pageNum) {
        try{
            int page = Integer.parseInt(pageNum);
            if(page < 0 || page > 100) throw new ValidationException("pageNum should be between 1 and 100 (inclusive).");
            return page;
        }catch (Exception e){
            throw new ValidationException("pageNum should be between 1 and 100 (inclusive).");
        }
    }

    public CompareCarRequest validateCompareCarsRequest(String cars, String hideSimilar) {
        if(cars == null || cars.isEmpty()){
            throw new ValidationException("Request must have atleast one car");
        }
        String[] carArray = cars.split(",");
        List<String> carList = Arrays.stream(carArray)
                .filter(car -> car != null)
                .filter(car -> !car.isEmpty())
                .collect(Collectors.toList());
        if(carList.isEmpty()) throw new ValidationException("Request must have atleast one car");
        boolean hide = false;
        if(hideSimilar != null && !hideSimilar.trim().isEmpty()){
            hide = "true".equalsIgnoreCase(hideSimilar);
        }

        return CompareCarRequest.builder()
                .hideSimilar(hide)
                .cars(carList)
                .build();
    }
}
