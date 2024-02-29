package com.bdtc.technews.service.news;

import com.bdtc.technews.dto.NewsDetailingDto;
import com.bdtc.technews.dto.NewsPreviewDto;
import com.bdtc.technews.dto.NewsRequestDto;
import com.bdtc.technews.model.News;
import com.bdtc.technews.repository.NewsRepository;
import com.bdtc.technews.service.news.utils.DateHandler;
import com.bdtc.technews.service.news.utils.TagHandler;
import com.bdtc.technews.service.tag.TagService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.UUID;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private DateHandler dateHandler;

    @Autowired
    private TagHandler tagHandler;

    @Transactional
    public NewsDetailingDto createNews(NewsRequestDto newsDto) {
        var news = new News(newsDto);
        var dateNow = dateHandler.getCurrentDateTime();
        news.setCreationDate(dateNow);
        news.setUpdateDate(dateNow);

        var tagSet = tagService.getTagSet(newsDto.tags());
        news.setTags(tagSet);

        newsRepository.save(news);
        return new NewsDetailingDto(
                news,
                tagHandler.convertSetTagToSetString(news.getTags()),
                dateHandler.formatDate(dateNow)
        );
    }

    public Page<NewsPreviewDto> getNewsPreview(Pageable pageable) {
        var newsPage = newsRepository.findAll(pageable);
        return newsPage.map(news -> new NewsPreviewDto(
                news,
                dateHandler.formatDate(news.getUpdateDate())
                )
        );
    }

    public NewsDetailingDto getNewsById(UUID newsId) {
        var news = newsRepository.getReferenceById(newsId);
        return new NewsDetailingDto(
                news,
                tagHandler.convertSetTagToSetString(news.getTags()),
                dateHandler.formatDate(news.getUpdateDate())
        );
    }
}
