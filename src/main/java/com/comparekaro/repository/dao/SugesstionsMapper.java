package com.comparekaro.repository.dao;

import com.comparekaro.repository.SuggestionRepository;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;

@Mapper
public interface SugesstionsMapper {
    @DaoFactory
    SuggestionRepository suggestionsDao(@DaoKeyspace CqlIdentifier keyspace);
}
