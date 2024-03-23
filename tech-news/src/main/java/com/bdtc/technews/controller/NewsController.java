package com.bdtc.technews.controller;

import com.bdtc.technews.dto.*;
import com.bdtc.technews.model.News;
import com.bdtc.technews.service.news.NewsService;
import com.bdtc.technews.service.news.backup.NewsBackupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
        NewsDetailingDto news = newsService.createNews(newsRequestDto);
        var uri = uriBuilder.path("tech-news/news/{id}").build(news.id());
        return ResponseEntity.created(uri).body(news);
    }

    @GetMapping("/preview")
    public ResponseEntity getNewsPreview(
            @PageableDefault() Pageable pageable,
            @RequestParam(name = "sortBy", required = false, defaultValue = "false") String sortBy) {
        Page<NewsPreviewDto> page = newsService.getNewsPreview(pageable, sortBy);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity getNewsById(@PathVariable UUID id) {
        NewsDetailingDto news = newsService.getNewsById(id);
        return ResponseEntity.ok(news);
    }

    @GetMapping()
    public ResponseEntity getNewsFilteringByTags(@RequestParam(name = "tags") String tags, @PageableDefault() Pageable pageable) {
        Page<NewsPreviewDto> news = newsService.getNewsPreviewFilteringByTags(pageable, tags);
        return ResponseEntity.ok(news);
    }

    @PatchMapping("/{id}/publish")
    public ResponseEntity publishNews(@PathVariable UUID id) {
        NewsDetailingDto news = newsService.publishNews(id);
        return ResponseEntity.ok(news);
    }

    @PatchMapping("/{id}/archive")
    public ResponseEntity archiveNews(@PathVariable UUID id) {
        NewsDetailingDto news = newsService.archiveNews(id);
        return ResponseEntity.ok(news);
    }

    @GetMapping("/archived")
    public ResponseEntity getArchivedNews(@PageableDefault() Pageable pageable) {
        Page<NewsPreviewDto> news = newsService.getArchivedNewsPreview(pageable);
        return ResponseEntity.ok(news);
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateNews(@ModelAttribute NewsUpdateDto updateDto, @PathVariable UUID id) {
        NewsDetailingDto news = newsService.updateNews(id, updateDto);
        return ResponseEntity.ok(news);
    }

    @GetMapping("/{id}/backup")
    public ResponseEntity getNewsBackup(@PathVariable UUID id, @RequestParam(name = "level") int backupLevel) {
        NewsBackupDto news = newsBackupService.getNewsBackup(id, backupLevel);
        return ResponseEntity.ok(news);
    }

    @PutMapping("/{id}/backup/{backupId}/restore")
    public ResponseEntity restoreNewsFromABackup(@PathVariable UUID id, @PathVariable Long backupId) {
        NewsDetailingDto news = newsBackupService.restoreNewsFromABackup(id, backupId);
        return ResponseEntity.ok(news);
    }
}
