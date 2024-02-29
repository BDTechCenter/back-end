package com.bdtc.technews.controller;

import com.bdtc.technews.dto.NewsDetailingDto;
import com.bdtc.technews.dto.NewsPreviewDto;
import com.bdtc.technews.dto.NewsRequestDto;
import com.bdtc.technews.service.news.NewsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @PostMapping
    public ResponseEntity createNews(@RequestBody @Valid NewsRequestDto newsRequestDto, UriComponentsBuilder uriBuilder) {
        var news = newsService.createNews(newsRequestDto);
        var uri = uriBuilder.path("tech-news/news/{id}").build(news.id());
        return ResponseEntity.created(uri).body(news);
    }

    @GetMapping("/preview")
    public ResponseEntity getNewsPreview(@RequestParam int amount, @PageableDefault() Pageable pageable) {
        pageable = PageRequest.ofSize(amount);
        var page = newsService.getNewsPreview(pageable);
        return ResponseEntity.ok(page);
    }
}
