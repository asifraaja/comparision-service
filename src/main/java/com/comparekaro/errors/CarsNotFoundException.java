package com.comparekaro.errors;

public class CarsNotFoundException extends RuntimeException{
    public CarsNotFoundException(String msg){
        super(msg);
    }
}
