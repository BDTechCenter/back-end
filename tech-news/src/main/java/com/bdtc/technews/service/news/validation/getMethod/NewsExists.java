package com.bdtc.technews.service.news.validation.getMethod;

import com.bdtc.technews.repository.NewsRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class NewsExists implements GetNewsValidators {

    @Autowired
    private NewsRepository newsRepository;

    @Override
    public void validate(UUID id) {
        var exists = newsRepository.existsById(id);
        if(!exists) throw new ValidationException(String.format("News with id '%s' doesn't exists!", id));
    }
}
