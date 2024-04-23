package com.bdtc.techradar.infra.exception.validation;

public class QuadrantAlreadyExistsException extends RuntimeException {

    public QuadrantAlreadyExistsException() {
        super("Quadrant with this ID already exists");
    }
}
