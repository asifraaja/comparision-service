package com.comparekaro.controller;

import com.comparekaro.helper.RequestValidator;
import com.comparekaro.helper.SuggestionResponseCreator;
import com.comparekaro.models.domain.SuggestionModel;
import com.comparekaro.models.request.CompareCarRequest;
import com.comparekaro.models.request.SuggestCarRequest;
import com.comparekaro.models.response.ComparisionDetails;
import com.comparekaro.service.ComparisonService;
import com.comparekaro.service.SuggestionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ComparisonControllerTest {
    @Mock
    private ComparisonService comparisionService;
    @Mock
    private SuggestionService suggestionService;
    @Mock
    private RequestValidator requestValidator;
    @Mock
    private SuggestionResponseCreator suggestionResponseCreator;
    @InjectMocks private ComparisonController classToTest;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void compareCars(){
        String s = "123,124", hideSimliar = "false";
        when(requestValidator.validateCompareCarsRequest(s, hideSimliar)).thenReturn(CompareCarRequest.builder().build());
        when(comparisionService.compareCars(any())).thenReturn(ComparisionDetails.builder().build());

        ResponseEntity resp = classToTest.compareCars(s, hideSimliar);
        assertNotNull(resp);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
    }

    @Test
    public void suggestCars(){
        when(requestValidator.validateSuggestionRequest(anyString(), anyString(), anyString())).thenReturn(SuggestCarRequest.builder().build());
        when(suggestionService.suggest(any())).thenReturn(SuggestionModel.builder().build());

        ResponseEntity resp = classToTest.suggestCars("123", "1", "10");
        assertNotNull(resp);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
    }
}
