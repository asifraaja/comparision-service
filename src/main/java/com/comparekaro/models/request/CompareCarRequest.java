package com.comparekaro.models.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CompareCarRequest {
    private List<String> cars;
    private boolean hideSimilar;
}
