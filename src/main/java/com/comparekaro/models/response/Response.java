package com.comparekaro.models.response;

import lombok.Builder;

import java.util.List;

@Builder
public class Response {
    List<Error> errors;
}
