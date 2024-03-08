package com.bdtc.technews.infra.exception.validation;

public class BusinessRuleException extends RuntimeException{
    public BusinessRuleException(String message) {
        super(message);
    }
}
