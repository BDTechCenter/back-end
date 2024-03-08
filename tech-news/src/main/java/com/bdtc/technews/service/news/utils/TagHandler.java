package com.bdtc.technews.service.news.utils;

import com.bdtc.technews.model.Tag;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TagHandler {

    public Set<String> convertSetTagToSetString(Set<Tag> tagSet) {
        Set<String> tagsStringSet = new HashSet<>();
        tagSet.forEach(tag -> tagsStringSet.add(tag.getName()));
        return tagsStringSet;
    }

}
