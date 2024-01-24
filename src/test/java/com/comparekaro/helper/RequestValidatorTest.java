package com.comparekaro.helper;

import com.comparekaro.errors.ValidationException;
import com.comparekaro.models.request.CompareCarRequest;
import com.comparekaro.models.request.SuggestCarRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class RequestValidatorTest {
    private RequestValidator classToTest = new RequestValidator();

    @Test(expected = ValidationException.class)
    public void validateEmptyCarIdForSuggestion(){
        classToTest.validateSuggestionRequest("", "1", "2");
    }

    @Test(expected = ValidationException.class)
    public void validateInvalidPageNum(){
        classToTest.validateSuggestionRequest("123", "-1", "10");
    }

    @Test(expected = ValidationException.class)
    public void validateInvalidPageSize(){
        classToTest.validateSuggestionRequest("123", "1", "-10");
    }

    @Test
    public void validSuggestRequest(){
        SuggestCarRequest request = classToTest.validateSuggestionRequest("123", "1", "2");
        assertEquals("123", request.getCarId());
        assertEquals(1, request.getPageNum());
        assertEquals(2, request.getPageSize());
    }

    @Test
    public void testDefaultPageConfigs(){
        SuggestCarRequest request = classToTest.validateSuggestionRequest("123", "", "");
        assertEquals("123", request.getCarId());
        assertEquals(1, request.getPageNum());
        assertEquals(10, request.getPageSize());
    }

    @Test(expected = ValidationException.class)
    public void testEmptyCarsForCompare(){
        classToTest.validateCompareCarsRequest("", "false");
    }

    @Test(expected = ValidationException.class)
    public void testEmptyCarsForCompare2(){
        classToTest.validateCompareCarsRequest(",,,", "false");
    }

    @Test
    public void validateCarRequest(){
        CompareCarRequest request = classToTest.validateCompareCarsRequest("123,124", "false");
        assertEquals("123", request.getCars().get(0));
        assertEquals("124", request.getCars().get(1));
        assertFalse(request.isHideSimilar());
    }

    @Test
    public void validateDefaultHideSimilar(){
        CompareCarRequest request = classToTest.validateCompareCarsRequest("123,124", "");
        assertEquals("123", request.getCars().get(0));
        assertEquals("124", request.getCars().get(1));
        assertFalse(request.isHideSimilar());
    }


}
