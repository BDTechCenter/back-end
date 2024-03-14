package com.bdtc.technews.infra.exception.validation;

public class ConflictInPathParameters extends RuntimeException{
    public ConflictInPathParameters(String message) {
        super(message);
    }
}
