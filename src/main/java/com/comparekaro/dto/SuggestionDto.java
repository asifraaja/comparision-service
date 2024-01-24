package com.comparekaro.dto;

import com.comparekaro.dto.udt.ItemUDT;
import com.datastax.oss.driver.api.mapper.annotations.ClusteringColumn;
import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import lombok.Builder;
import lombok.Data;

import com.datastax.oss.driver.api.mapper.annotations.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Entity(defaultKeyspace = "compare_karo")
@CqlName("suggestions")
@NoArgsConstructor
public class SuggestionDto {
    @PartitionKey
    @CqlName("id")
    private String id;

    @CqlName("name")
    private String name;

    @CqlName("model_name")
    private String modelName;

    @ClusteringColumn
    @CqlName("company")
    private String company;

    @CqlName("items")
    private List<ItemUDT> similarCars;
}
