package com.comparekaro.service;

import com.comparekaro.dto.SuggestionDto;
import com.comparekaro.dto.udt.ItemUDT;
import com.comparekaro.mappers.SuggestionDtoToModelMapper;
import com.comparekaro.models.domain.SuggestionModel;
import com.comparekaro.models.request.SuggestCarRequest;
import jnr.ffi.annotations.In;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SuggestionServiceTest {
    @Mock private CacheService cacheService;
    private SuggestionDtoToModelMapper suggestionDtoToModelMapper;
    @InjectMocks SuggestionService classToTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        classToTest = new SuggestionService(cacheService, new SuggestionDtoToModelMapper());
    }

    @Test
    public void emptySuggestions(){
        SuggestCarRequest request = SuggestCarRequest.builder().carId("123").build();
        when(cacheService.getSuggestions(anyString())).thenReturn(null);

        SuggestionModel resp = classToTest.suggest(request);
        assertNull(resp.getId());
        assertEquals(0, resp.getEndIndex());
        assertEquals(0, resp.getStartIndex());
        assertEquals(0, resp.getTotalCars());
    }

    @Test
    public void fetchSuggestions(){
        String carId = "123";
        SuggestCarRequest request = SuggestCarRequest.builder().carId(carId).pageNum(1).pageSize(5).build();
        when(cacheService.getSuggestions(anyString())).thenReturn(createSuggestionDto(carId, 5));

        SuggestionModel resp = classToTest.suggest(request);
        assertNotNull(resp);
        assertEquals(5, resp.getSimilarCars().size());
        assertEquals(1, resp.getPageNum());
        assertEquals(0, resp.getStartIndex());
        assertEquals(5, resp.getEndIndex());
    }

    @Test
    public void fetchPaginated(){
        String carId = "123";
        SuggestCarRequest request = SuggestCarRequest.builder().carId(carId).pageNum(1).pageSize(2).build();
        when(cacheService.getSuggestions(anyString())).thenReturn(createSuggestionDto(carId, 5));

        SuggestionModel resp = classToTest.suggest(request);
        assertNotNull(resp);
        assertEquals(2, resp.getSimilarCars().size());
        assertEquals(1, resp.getPageNum());
        assertEquals(0, resp.getStartIndex());
        assertEquals(2, resp.getEndIndex());

        request = SuggestCarRequest.builder().carId(carId).pageNum(2).pageSize(2).build();
        resp = classToTest.suggest(request);
        assertNotNull(resp);
        assertEquals(2, resp.getSimilarCars().size());
        assertEquals(2, resp.getPageNum());
        assertEquals(2, resp.getStartIndex());
        assertEquals(4, resp.getEndIndex());
    }

    private SuggestionDto createSuggestionDto(String carId, int size) {
        SuggestionDto dto = new SuggestionDto();
        List<ItemUDT> list = new ArrayList<>();
        for(int i=0; i<size; i++){
            ItemUDT udt = new ItemUDT();
            udt.setId(String.valueOf(i));
            udt.setName(String.valueOf(i));
            list.add(udt);
        }
        dto.setId(carId);
        dto.setSimilarCars(list);
        return dto;
    }
}
