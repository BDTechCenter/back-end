package com.bdtc.technews.service.news;

import com.bdtc.technews.dto.NewsDetailingDto;
import com.bdtc.technews.dto.NewsRequestDto;
import com.bdtc.technews.model.News;
import com.bdtc.technews.repository.NewsRepository;
import com.bdtc.technews.service.tag.TagService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private TagService tagService;

    @Transactional
    public NewsDetailingDto createNews(NewsRequestDto newsDto) {
        var news = new News(newsDto);
        var dateNow = getCurrentDateTime();
        news.setCreationDate(dateNow);
        news.setUpdateDate(dateNow);

        var tagSet = tagService.getTagSet(newsDto.tags());
        news.setTags(tagSet);

        newsRepository.save(news);
        return new NewsDetailingDto(news, newsDto.tags(), formatDate(dateNow));
    }

    private LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    private String formatDate(LocalDateTime dateNow) {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        return dateNow.format(formatter);
    }
}
