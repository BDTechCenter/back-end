package com.bdtc.technews.contants;

import com.bdtc.technews.infra.exception.validation.ConflictInPathParameters;

public enum FilterOption {
    VIEW,
    LATEST,
    RELEVANCE,
    PUBLISHED,
    ARCHIVED,
    EMPTY;

    public static FilterOption stringToFilterOption(String valor) {
        try {
            return FilterOption.valueOf(valor.toUpperCase());
        }catch (IllegalArgumentException exception) {
            throw new ConflictInPathParameters("Can't receive more then one path parameter");
        }

    }
}
