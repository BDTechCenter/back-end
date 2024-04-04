package com.bdtc.technews.infra.exception.validation;

public class UnauthorizedByRoles extends RuntimeException{
    public UnauthorizedByRoles() {
        super("Unauthorized by current roles to perform this transaction");
    }
}
