package com.comparekaro.dto.document;

import com.comparekaro.models.response.PriceOverview;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;


@Document(collection = "car_information")
@Data
public class CarInfoMDto {
    @Id
    private String id;
    @Field("car_id")
    private String carId;
    @Field("name")
    private String name;
    @Field("model_id")
    private String modelId;
    @Field("model_name")
    private String modelName;
    @Field("company")
    private String company;
    @Field("year")
    private int year;
    @Field("images")
    private List<String> images;
    @Field("colors")
    private List<String> colors;
    @Field("min_price_details")
    private PriceDetailsMDto minPriceDetails;
    @Field("max_price_details")
    private PriceDetailsMDto maxPriceDetails;
    @Field("specifications")
    private List<SpecInfoMDto> specifications;
    @Field("features")
    private List<SpecInfoMDto> features;
}
