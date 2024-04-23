package com.bdtc.techradar.infra.exception.validation;

public class PermissionException extends RuntimeException{
    public PermissionException() {
        super("You don't have permission!");
    }
}
