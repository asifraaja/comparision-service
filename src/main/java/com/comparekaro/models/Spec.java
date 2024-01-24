package com.comparekaro.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Spec {
    private String group;
    private int groupOrder;
    private String name;
    private String description;
    private int sortOrder;
}
