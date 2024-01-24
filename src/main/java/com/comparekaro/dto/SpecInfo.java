package com.comparekaro.dto;

import lombok.Data;

@Data
public class SpecInfo {
    private String name;
    private String value;
    private String unitType;

    public SpecInfo(String name, String value, String unitType) {
        this.name = name;
        this.value = value;
        this.unitType = unitType;
    }
}
