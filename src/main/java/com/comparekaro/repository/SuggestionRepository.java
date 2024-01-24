package com.comparekaro.repository;

import com.comparekaro.dto.SuggestionDto;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import org.springframework.stereotype.Repository;

@Dao
public interface SuggestionRepository {
    @Select(customWhereClause = "id= :carId")
    SuggestionDto findByCarId(String carId);
}
