package com.bdtc.techradar.infra.exception.validation;

public class ItemAlreadyPublishedException extends RuntimeException {
    public ItemAlreadyPublishedException() {
        super("Item already published!");
    }
}
