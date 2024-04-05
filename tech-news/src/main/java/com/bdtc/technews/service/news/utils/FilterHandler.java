package com.bdtc.technews.service.news.utils;

import com.bdtc.technews.infra.exception.validation.ConflictInPathParameters;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FilterHandler {
    private List<String> newsPreviewFilterOptions = List.of("view", "latest", "relevance");

    public void validateNewsPreviewFilter(String sortBy) {
        boolean validOption=false;
        for(String option : newsPreviewFilterOptions) {
            if(sortBy.equals(option)) {
                validOption=true;
                break;
            }
        }
        if(!validOption) throw new ConflictInPathParameters("Invalid option for filter! Choose between 'view', 'latest' and 'relevance'!");
    }

    private List<String> newsByAuthorFilterOptions = List.of("published", "archived");

    public void validateNewsByAuthorFilter(String sortBy) {
        boolean validOption=false;
        for(String option : newsByAuthorFilterOptions) {
            if(sortBy.equals(option)) {
                validOption=true;
                break;
            }
        }
        if(!validOption) throw new ConflictInPathParameters("Invalid option for filter! Choose between 'published' and 'archived'!");
    }
}
