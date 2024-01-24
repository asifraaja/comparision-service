package com.comparekaro.dto.document;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class SpecInfoMDto {
    @Field("name") private String name;
    @Field("value")private String value;
    @Field("unit") private String unit;
}
