package com.bdtc.technews.service.news.utils;

import com.bdtc.technews.infra.exception.validation.ConflictInPathParameters;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FilterHandler {

    public void validateFilter(List<String> filterOptions, String filter) {
        boolean validOption=false;
        for(String option : filterOptions) {
            if(filter.equals(option)) {
                validOption=true;
                break;
            }
        }
        if(!validOption) throw new ConflictInPathParameters(
                String.format(
                        "Invalid option for filter! Choose between %s",
                        listToString(filterOptions)
                )
        );
    }

    // UTILS FOR VALIDATE FILTER
    public String listToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i != list.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
