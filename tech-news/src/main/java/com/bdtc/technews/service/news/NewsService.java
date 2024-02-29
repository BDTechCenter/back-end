package com.bdtc.technews.service.news;

import com.bdtc.technews.dto.NewsDetailingDto;
import com.bdtc.technews.dto.NewsRequestDto;
import com.bdtc.technews.model.News;
import com.bdtc.technews.model.Tag;
import com.bdtc.technews.repository.NewsRepository;
import com.bdtc.technews.service.tag.TagService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private TagService tagService;

    @Transactional
    public NewsDetailingDto createNews(NewsRequestDto newsDto) {
        var news = new News(newsDto);
        news.setCreationDate(currentDateTime());
        news.setUpdateDate(currentDateTime());

        var tagSet = tagService.getTagSet(newsDto.tags());
        news.setTags(tagSet);

        newsRepository.save(news);
        return new NewsDetailingDto(news, newsDto.tags());
    }

    private LocalDateTime currentDateTime() {
        return LocalDateTime.now();
    }
}
