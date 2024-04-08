package com.bdtc.technews.contants;

public enum FilterOption {
    VIEW,
    LATEST,
    RELEVANCE,
    PUBLISHED,
    ARCHIVED,
    EMPTY;

    public static FilterOption stringToFilterOption(String valor) {
        return FilterOption.valueOf(valor.toUpperCase());
    }
}
