package com.comparekaro.models.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SuggestCarRequest {
    private String carId;
    private int pageNum;
    private int pageSize;
}
