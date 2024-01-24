package com.comparekaro.service;

import com.comparekaro.dto.SpecInfo;
import com.comparekaro.errors.CarNotFoundException;
import com.comparekaro.errors.CarsNotFoundException;
import com.comparekaro.helper.ComparisonDetailsResponseCreator;
import com.comparekaro.models.domain.FullCarInfo;
import com.comparekaro.models.request.CompareCarRequest;
import com.comparekaro.models.response.ComparisionDetails;
import com.comparekaro.models.response.Metadata;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ComparisonServiceTest {
    @Mock
    private CacheService cacheService;
    @Mock private ComparisonDetailsResponseCreator comparisonDetailsResponseCreator;
    @InjectMocks
    private ComparisonService classToTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test(expected = CarsNotFoundException.class)
    public void testForEmptyCarRequest(){
        CompareCarRequest request = CompareCarRequest.builder().cars(new ArrayList<>()).hideSimilar(false).build();
        classToTest.compareCars(request);
    }

    @Test(expected = CarsNotFoundException.class)
    public void testWhenCarInfoNotFound(){
        when(cacheService.getCarInfo(anyString())).thenReturn(FullCarInfo.builder().build());
        CompareCarRequest request = CompareCarRequest.builder().cars(List.of("123","234")).hideSimilar(false).build();

        classToTest.compareCars(request);
    }

    @Test
    public void testComparisonDetails(){
        FullCarInfo car123 = dummyResponseFor123();
        when(cacheService.getCarInfo(anyString())).thenReturn(car123);
        when(comparisonDetailsResponseCreator.generator(anyMap(), anyList(), any())).thenReturn(dummyComparison());

        List<String> cars = List.of("123", "234");
        CompareCarRequest request = CompareCarRequest.builder().cars(cars).hideSimilar(false).build();

        ComparisionDetails details = classToTest.compareCars(request);
        assertEquals("Comparing two cars", details.getMetadata().getComparisionText());

        verify(comparisonDetailsResponseCreator, times(1)).generator(anyMap(), anyList(), any());
    }

    @Test
    public void fewCarsFound(){
        FullCarInfo car123 = dummyResponseFor123();
        when(cacheService.getCarInfo("123")).thenReturn(car123);
        doThrow(new CarNotFoundException("not found")).when(cacheService).getCarInfo("124");
        when(comparisonDetailsResponseCreator.generator(anyMap(), anyList(), any())).thenReturn(dummyComparison());

        List<String> cars = List.of("123", "124");
        CompareCarRequest request = CompareCarRequest.builder().cars(cars).hideSimilar(false).build();

        ComparisionDetails details = classToTest.compareCars(request);

        verify(comparisonDetailsResponseCreator, times(1))
                .generator(Map.of("123", car123), List.of("123"), request);
    }

    private ComparisionDetails dummyComparison() {
        return ComparisionDetails.builder()
                .metadata(Metadata.builder().comparisionText("Comparing two cars").build())
                .build();
    }

    private FullCarInfo dummyResponseFor123() {
        return FullCarInfo.builder()
                .carId("123")
                .name("car1")
                .images(null)
                .company("company1")
                .specifications(List.of(
                        new SpecInfo("Engine", "Turbo Engine", ""),
                        new SpecInfo("Mileage", "14.5", "kmpl")
                ))
                .features(List.of(
                        new SpecInfo("Acceleration", "6.2", "seconds"),
                        new SpecInfo("Airbags", "2", "")

                ))
                .build();
    }

    private FullCarInfo dummyResponseFor124() {
        return FullCarInfo.builder()
                .carId("124")
                .name("car2")
                .images(null)
                .company("company2")
                .specifications(List.of(
                        new SpecInfo("Engine", "Turbo Engine", ""),
                        new SpecInfo("Mileage", "14.6", "kmpl")
                ))
                .features(List.of(
                        new SpecInfo("Acceleration", "6.1", "seconds"),
                        new SpecInfo("Airbags", "2", "")

                ))
                .build();
    }


}
