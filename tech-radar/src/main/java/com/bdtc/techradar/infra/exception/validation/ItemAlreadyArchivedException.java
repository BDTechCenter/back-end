package com.bdtc.techradar.infra.exception.validation;

public class ItemAlreadyArchivedException extends RuntimeException {
    public ItemAlreadyArchivedException() {
        super("Item already archived!");
    }
}
