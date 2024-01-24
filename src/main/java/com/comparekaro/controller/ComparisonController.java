package com.comparekaro.controller;

import com.comparekaro.dto.SuggestionDto;
import com.comparekaro.errors.CarNotFoundException;
import com.comparekaro.errors.CarsNotFoundException;
import com.comparekaro.errors.SuggestionNotFoundException;
import com.comparekaro.helper.RequestValidator;
import com.comparekaro.helper.SuggestionResponseCreator;
import com.comparekaro.models.domain.SuggestionModel;
import com.comparekaro.models.request.CompareCarRequest;
import com.comparekaro.models.request.SuggestCarRequest;
import com.comparekaro.models.response.BasicCarInfo;
import com.comparekaro.models.response.ComparisionDetails;
import com.comparekaro.models.response.Image;
import com.comparekaro.models.response.suggestion.RespError;
import com.comparekaro.models.response.suggestion.SuggestionResponse;
import com.comparekaro.service.ComparisonService;
import com.comparekaro.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ComparisonController {
    @Autowired
    private ComparisonService comparisionService;
    @Autowired
    private SuggestionService suggestionService;
    @Autowired
    private RequestValidator requestValidator;
    @Autowired
    private SuggestionResponseCreator suggestionResponseCreator;
    @RequestMapping(value = "/compare", method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public ResponseEntity compareCars(@RequestParam(value = "cars") String cars,
                                      @RequestParam(value = "hideSimilar", required = false, defaultValue = "false") String hideSimilar){
            CompareCarRequest request = requestValidator.validateCompareCarsRequest(cars, hideSimilar);
            ComparisionDetails response = comparisionService.compareCars(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/suggest", method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public ResponseEntity suggestCars(@RequestParam(value = "carId") String carId,
                                      @RequestParam(value = "pageSize", required = false, defaultValue = "10") String pageSize,
                                      @RequestParam(value = "pageNum", required = false, defaultValue = "1") String pageNum){
            SuggestCarRequest request = requestValidator.validateSuggestionRequest(carId, pageNum, pageSize);
            SuggestionModel similarCars = suggestionService.suggest(request);
            SuggestionResponse response = suggestionResponseCreator.createResp(similarCars);
            return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
