package com.comparekaro.service;

import com.comparekaro.dto.SuggestionDto;
import com.comparekaro.dto.udt.ItemUDT;
import com.comparekaro.errors.CarNotFoundException;
import com.comparekaro.errors.SuggestionNotFoundException;
import com.comparekaro.models.domain.FullCarInfo;
import com.comparekaro.repository.CassandraRepository;
import com.comparekaro.repository.FullCarInfoRepository;
import com.comparekaro.repository.SuggestionRepository;
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
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CacheServiceTest {
    @Mock private CassandraRepository cassandraRepository;
    @Mock private FullCarInfoRepository fullCarInfoRepository;
    @Mock private SuggestionRepository suggestionRepository;
    @InjectMocks private CacheService classToTest;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        classToTest.initialize();
    }

    @Test(expected = SuggestionNotFoundException.class)
    public void whenSuggesstionsNotFoundTest(){
        when(cassandraRepository.getSuggestionRepository()).thenReturn(suggestionRepository);
        doThrow(new IllegalArgumentException("illegal")).when(suggestionRepository).findByCarId(anyString());

        classToTest.getSuggestions("123");
    }

    @Test
    public void getSuggestionsTest(){
        String carId = "123";
        when(cassandraRepository.getSuggestionRepository()).thenReturn(suggestionRepository);
        when(suggestionRepository.findByCarId(anyString())).thenReturn(createSuggestionDto(carId, 5));

        SuggestionDto suggestions = classToTest.getSuggestions(carId);
        assertNotNull(suggestions);
        assertNotNull(suggestions.getId());
        assertEquals(5, suggestions.getSimilarCars().size());
    }

    @Test(expected = CarNotFoundException.class)
    public void whenCarInfoIsNotFound(){
        String carId = "123";
        doThrow(new NoSuchElementException("no element")).when(fullCarInfoRepository).findByCarId(anyString());

        classToTest.getCarInfo(carId);
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
