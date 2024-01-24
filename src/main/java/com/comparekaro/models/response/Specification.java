package com.comparekaro.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Specification {
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("unitType")
    private String unitType;
    @JsonProperty("sortedOrder")
    private int sortedOrder;
    @JsonProperty("hasAtleastOneValue")
    boolean hasAtleastOneValue;
    @JsonProperty("values")
    private List<String> values;

    public Specification(Specification spec){
        if(spec == null) return;
        this.name = spec.getName();
        this.description = spec.getDescription();
        this.unitType = spec.getUnitType();
        this.sortedOrder = spec.getSortedOrder();
        this.hasAtleastOneValue = spec.isHasAtleastOneValue();
        this.values = new ArrayList<>(spec.getValues());
    }
}
