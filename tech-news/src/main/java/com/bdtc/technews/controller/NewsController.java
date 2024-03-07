package com.bdtc.technews.controller;

import com.bdtc.technews.dto.NewsRequestDto;
import com.bdtc.technews.dto.NewsUpdateDto;
import com.bdtc.technews.service.news.NewsService;
import com.bdtc.technews.service.news.backup.NewsBackupService;
import jakarta.validation.Valid;
import jakarta.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private NewsBackupService newsBackupService;

    @PostMapping
    public ResponseEntity createNews(@ModelAttribute @Valid NewsRequestDto newsRequestDto, UriComponentsBuilder uriBuilder) {
        var news = newsService.createNews(newsRequestDto);
        var uri = uriBuilder.path("tech-news/news/{id}").build(news.id());
        return ResponseEntity.created(uri).body(news);
    }

    @GetMapping("/preview")
    public ResponseEntity getNewsPreview(@PageableDefault() Pageable pageable, @RequestParam(name = "sortByView", required = false, defaultValue = "false") boolean sortByView) {
        var page = newsService.getNewsPreview(pageable, sortByView);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity getNewsById(@PathVariable UUID id) {
        var news = newsService.getNewsById(id);
        return ResponseEntity.ok(news);
    }

    @GetMapping()
    public ResponseEntity getNewsFilteringByTags(@RequestParam(name = "tags") String tags, @PageableDefault() Pageable pageable) {
        var news = newsService.getNewsPreviewFilteringByTags(pageable, tags);
        return ResponseEntity.ok(news);
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateNews(@ModelAttribute NewsUpdateDto updateDto, @PathVariable UUID id) {
        var news = newsService.updateNews(id, updateDto);
        return ResponseEntity.ok(news);
    }

    @GetMapping("/{id}/backup")
    public ResponseEntity getNewsBackup(@PathVariable UUID id, @RequestParam(name = "level") int backupLevel) {
        var news = newsBackupService.getNewsBackup(id, backupLevel);
        return ResponseEntity.ok(news);
    }
}
