package com.comparekaro.helper;

import com.comparekaro.dto.SuggestionDto;
import com.comparekaro.dto.udt.ItemUDT;
import com.comparekaro.models.domain.SuggestionModel;
import com.comparekaro.models.response.suggestion.SuggestionResponse;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SuggestionResponseCreatorTest {
    private SuggestionResponseCreator classToTest = new SuggestionResponseCreator();

    @Test
    public void testNullSuggestion(){
        SuggestionResponse resp = classToTest.createResp(null);
        assertNotNull(resp);
        assertNull(resp.getBasicCarInfo());
        assertNull(resp.getErrors());
        assertNull(resp.getSimilarCars());
        assertEquals(0, resp.getTotalCars());
    }

    @Test
    public void testCreateSuggestion(){
        SuggestionModel model = SuggestionModel.builder()
                .id("123")
                .name("a")
                .company("c")
                .similarCars(List.of(
                        SuggestionModel.builder().id("1").build(),
                        SuggestionModel.builder().id("2").build()
                        ))
                .build();
        SuggestionResponse resp = classToTest.createResp(model);
        assertNotNull(resp);
        assertEquals(model.getId(), resp.getBasicCarInfo().getId());
        assertEquals(model.getName(), resp.getBasicCarInfo().getName());
        assertEquals(model.getCompany(), resp.getBasicCarInfo().getCompany());
        assertEquals(2, resp.getSimilarCars().size());
    }

    private ItemUDT createItemUDT(String carId, String carName) {
        ItemUDT item = new ItemUDT();
        item.setId(carId);
        item.setName(carName);
        return item;
    }
}
