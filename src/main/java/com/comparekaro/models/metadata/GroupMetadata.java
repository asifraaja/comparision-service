package com.comparekaro.models.metadata;

import lombok.Data;

import java.util.List;

@Data
public class GroupMetadata {
    private String group;
    private int groupOrder;
    private List<SpecMetadata> specifications;
}
