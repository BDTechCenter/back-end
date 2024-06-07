package com.bdtc.techradar.constant;

import com.bdtc.techradar.infra.exception.validation.ConflictInPathParameters;

public enum ItemFilter {

    PUBLISHED,
    ARCHIVED,
    ALL;

    public static ItemFilter stringToFilterOption(String valor) {
        try {
            return ItemFilter.valueOf(valor.toUpperCase());
        }catch (IllegalArgumentException exception) {
            throw new ConflictInPathParameters("Can't receive more then one path parameter");
        }

    }

}
