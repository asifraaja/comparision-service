package com.comparekaro.errors;

public class SuggestionNotFoundException extends RuntimeException{
    public SuggestionNotFoundException(String msg){
        super(msg);
    }
}
