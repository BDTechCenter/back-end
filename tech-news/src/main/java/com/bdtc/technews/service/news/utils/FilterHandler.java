package com.bdtc.technews.service.news.utils;

import com.bdtc.technews.infra.exception.validation.ConflictInPathParameters;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FilterHandler {
//    private List<String> newsPreviewFilterOptions = List.of("view", "latest", "relevance");

//    public void validateNewsPreviewFilter(String sortBy) {
//        boolean validOption=false;
//        for(String option : newsPreviewFilterOptions) {
//            if(sortBy.equals(option)) {
//                validOption=true;
//                break;
//            }
//        }
//        if(!validOption) throw new ConflictInPathParameters("Invalid option for filter! Choose between 'view', 'latest' and 'relevance'!");
//    }
//
//    private List<String> newsByAuthorFilterOptions = List.of("published", "archived");
//
//    public void validateNewsByAuthorFilter(String sortBy) {
//        boolean validOption=false;
//        for(String option : newsByAuthorFilterOptions) {
//            if(sortBy.equals(option)) {
//                validOption=true;
//                break;
//            }
//        }
////        if(!validOption) throw new ConflictInPathParameters("Invalid option for filter! Choose between 'published' and 'archived'!");
//        if(!validOption) throw new ConflictInPathParameters(String.format("Invalid option for filter! Choose between %s", buildString(newsByAuthorFilterOptions)));
//    }

    public void validateFilter(List<String> filterOptions, String filter) {
        boolean validOption=false;
        for(String option : filterOptions) {
            if(filter.equals(option)) {
                validOption=true;
                break;
            }
        }
        if(!validOption) throw new ConflictInPathParameters(String.format("Invalid option for filter! Choose between %s", buildString(filterOptions)));
    }

    public String buildString(List<String> list) {
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
