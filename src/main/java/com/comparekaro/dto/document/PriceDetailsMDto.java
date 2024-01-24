package com.comparekaro.dto.document;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class PriceDetailsMDto {
    @Field("price") private String price;
    @Field("formatted_price") private String formattedPrice;
}
