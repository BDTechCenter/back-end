package com.bdtc.technews.infra.exception.validation;

public class UnauthorizedByRolesException extends RuntimeException{
    public UnauthorizedByRolesException() {
        super("Unauthorized by current roles to perform this transaction");
    }
}
