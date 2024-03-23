package com.bdtc.technews.service.news.utils;

import com.bdtc.technews.infra.exception.validation.ConflictInPathParameters;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FilterHandler {
    private List<String> filterOptions = List.of("view", "latest");

    public void validateFilter(String sortBy) {
        boolean validOption=false;
        for(String option : filterOptions) {
            if(sortBy.equals(option)) {
                validOption=true;
                break;
            }
        }
        if(!validOption) throw new ConflictInPathParameters("Invalid option for filter! Choose between 'view' and 'latest'!");
    }
}
