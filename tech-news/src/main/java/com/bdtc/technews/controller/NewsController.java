package com.bdtc.technews.controller;

import com.bdtc.technews.dto.*;
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
    public ResponseEntity createNews(@RequestHeader("Authorization") String tokenJWT, @ModelAttribute @Valid NewsRequestDto newsRequestDto, UriComponentsBuilder uriBuilder) {
        NewsDetailingDto news = newsService.createNews(tokenJWT, newsRequestDto);
        var uri = uriBuilder.path("tech-news/news/{id}").build(news.id());
        return ResponseEntity.created(uri).body(news);
    }

    @GetMapping("/preview")
    public ResponseEntity getNewsPreview(
            @PageableDefault() Pageable pageable,
            @RequestParam(name = "sortBy", required = false, defaultValue = "latest") FilterOption sortBy,
            @RequestParam(name = "title", required = false, defaultValue = "") String titleFilter,
            @RequestParam(name = "tags", required = false, defaultValue = "") String tags
    ) {
        Page<NewsPreviewDto> page = newsService.getNewsPreview(pageable, sortBy, titleFilter, tags);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity getNewsById(@RequestHeader("Authorization") String tokenJWT, @PathVariable UUID id) {
        NewsDetailingWUpVoteDto news = newsService.getNewsById(tokenJWT, id);
        return ResponseEntity.ok(news);
    }

    @PatchMapping("/{id}/publish")
    public ResponseEntity publishNews(@RequestHeader("Authorization") String tokenJWT, @PathVariable UUID id) {
        NewsDetailingDto news = newsService.publishNews(tokenJWT, id);
        return ResponseEntity.ok(news);
    }

    @PatchMapping("/{id}/archive")
    public ResponseEntity archiveNews(@RequestHeader("Authorization") String tokenJWT, @PathVariable UUID id) {
        NewsDetailingDto news = newsService.archiveNews(tokenJWT, id);
        return ResponseEntity.ok(news);
    }

//    @GetMapping("/archived")
//    public ResponseEntity getArchivedNews(@PageableDefault() Pageable pageable) {
//        Page<NewsPreviewDto> news = newsService.getArchivedNewsPreview(pageable);
//        return ResponseEntity.ok(news);
//    }

    @GetMapping("/author")
    public ResponseEntity getNewsBasedOnCurrentUserAuthor(
            @RequestHeader("Authorization") String tokenJWT,
            @PageableDefault() Pageable pageable,
            @RequestParam(name = "sortBy", required = false, defaultValue = "empty") FilterOption sortBy) {
        Page<NewsPreviewDto> news = newsService.getNewsByAuthor(tokenJWT, pageable, sortBy);
        return ResponseEntity.ok(news);
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateNews(@RequestHeader("Authorization") String tokenJWT, @ModelAttribute NewsUpdateDto updateDto, @PathVariable UUID id) {
        NewsDetailingDto news = newsService.updateNews(tokenJWT, id, updateDto);
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

    @PatchMapping("/{id}/upvote")
    public ResponseEntity addUpVoteToNews(@RequestHeader("Authorization") String tokenJWT, @PathVariable UUID id) {
        newsService.addUpVoteToNews(tokenJWT, id);
        return ResponseEntity.ok().build();
    }
}
