package com.comparekaro.models.metadata;

import lombok.Data;

import java.util.List;

@Data
public class SpecificationMetadata {
    private List<GroupMetadata> groups;
}
