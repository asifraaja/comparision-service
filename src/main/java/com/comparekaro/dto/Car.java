package com.comparekaro.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@TypeDef(
//        name = "string-array",
//        typeClass = StringArrayType.class
//)

@Entity
@Table(name = "car_info")
@Data
public class Car {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "model_id")
    private String modelId;
    @Column(name = "model_name")
    private String modelName;
    @Column(name = "company")
    private String company;
    @Column(name = "year")
    private int year;
//    @Column(name = "images")
    private String[] images;
    @Column(name = "min_price")
    private String minPrice;
    @Column(name = "min_price_formatted")
    private String minPriceFormatted;
    @Column(name = "max_price")
    private String maxPrice;
    @Column(name = "max_price_formatted")
    private String maxPriceFormatted;
//    @Column(name = "colors")
    private String[] colors;
}
