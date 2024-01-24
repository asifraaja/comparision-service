package com.comparekaro.dto.udt;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import lombok.Data;

@Entity(defaultKeyspace = "compare_karo")
@CqlName("items")
@Data
public class ItemUDT {
    @CqlName("id")
    private String id;
    @CqlName("name")
    private String name;
    @CqlName("model_name")
    private String modelName;
    @CqlName("company")
    private String company;
}
