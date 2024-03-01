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

import java.util.UUID;

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
    public ResponseEntity getNewsPreview(@PageableDefault() Pageable pageable) {
        var page = newsService.getNewsPreview(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity getNewsById(@PathVariable UUID id) {
        var news = newsService.getNewsById(id);
        return ResponseEntity.ok(news);
    }
}
