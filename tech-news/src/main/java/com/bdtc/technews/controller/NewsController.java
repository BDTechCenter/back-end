package com.bdtc.technews.controller;

import com.bdtc.technews.dto.news.*;
import com.bdtc.technews.service.news.NewsService;
import com.bdtc.technews.service.news.backup.NewsBackupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Tag(name = "News Controller", description = "Handle all news related requests") // #spring-doc
@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private NewsBackupService newsBackupService;

    @Operation(summary = "Create a new News") // #spring-doc
    @PostMapping
    public ResponseEntity<NewsDetailingDto> createNews(
            @AuthenticationPrincipal Jwt tokenJWT,
            @ModelAttribute @Valid NewsRequestDto newsRequestDto,
            UriComponentsBuilder uriBuilder
    ) {
        NewsDetailingDto news = newsService.createNews(tokenJWT, newsRequestDto);
        var uri = uriBuilder.path("tech-news/news/{id}").build(news.id());
        return ResponseEntity.created(uri).body(news);
    }

    @Operation(
            summary = "Get published news preview",
            parameters = {
                @Parameter(
                        name = "sortBy",
                        description = "choose between: 'view' (most viewed), 'latest' and 'relevance' (based on upVotes)",
                        required = false
                ),
                @Parameter(name = "title", description = "filter the news by specific title", required = false),
                @Parameter(name = "tags", description = "filter the news by specific tags", required = false)
            }
    ) // #spring-doc
    @GetMapping("/preview")
    public ResponseEntity<Page<NewsPreviewDto>> getNewsPreview(
            @PageableDefault() Pageable pageable,
            @RequestParam(name = "sortBy", required = false, defaultValue = "latest") String sortBy,
            @RequestParam(name = "title", required = false, defaultValue = "") String titleFilter,
            @RequestParam(name = "tags", required = false, defaultValue = "") String tags
    ) {
        Page<NewsPreviewDto> page = newsService.getNewsPreview(pageable, sortBy, titleFilter, tags);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Get full news details based on id") // #spring-doc
    @GetMapping("/{id}")
    public ResponseEntity<NewsDetailingWUpVoteDto> getNewsById(
            @AuthenticationPrincipal Jwt tokenJWT,
            @PathVariable UUID id
    ) {
        NewsDetailingWUpVoteDto news = newsService.getNewsById(tokenJWT, id);
        return ResponseEntity.ok(news);
    }

    @Operation(summary = "Publish an archived news") // #spring-doc
    @PatchMapping("/{id}/publish")
    public ResponseEntity<NewsDetailingDto> publishNews(
            @AuthenticationPrincipal Jwt tokenJWT,
            @PathVariable UUID id
    ) {
        NewsDetailingDto news = newsService.publishNews(tokenJWT, id);
        return ResponseEntity.ok(news);
    }

    @Operation(summary = "Archive a published news") // #spring-doc
    @PatchMapping("/{id}/archive")
    public ResponseEntity<NewsDetailingDto> archiveNews(
            @AuthenticationPrincipal Jwt tokenJWT,
            @PathVariable UUID id
    ) {
        NewsDetailingDto news = newsService.archiveNews(tokenJWT, id);
        return ResponseEntity.ok(news);
    }

    @Operation(
            summary = "Get news based on author (logged user)",
            parameters = {
                @Parameter(name = "sortBy",description = "choose between 'published' and 'archived'", required = false)
            }
    ) // #spring-doc
    @GetMapping("/author")
    public ResponseEntity<Page<NewsPreviewDto>> getNewsBasedOnAuthor(
            @AuthenticationPrincipal Jwt tokenJWT,
            @PageableDefault() Pageable pageable,
            @RequestParam(name = "sortBy", required = false, defaultValue = "empty") String sortBy
    ) {
        Page<NewsPreviewDto> news = newsService.getNewsByAuthor(tokenJWT, pageable, sortBy);
        return ResponseEntity.ok(news);
    }

    @Operation(summary = "Update a news") // #spring-doc
    @PatchMapping("/{id}")
    public ResponseEntity<NewsDetailingDto> updateNews(
            @AuthenticationPrincipal Jwt tokenJWT,
            @ModelAttribute NewsUpdateDto updateDto,
            @PathVariable UUID id
    ) {
        NewsDetailingDto news = newsService.updateNews(tokenJWT, id, updateDto);
        return ResponseEntity.ok(news);
    }

    @Operation(summary = "Add or remove a upvote to measure news relevance") // #spring-doc
    @PatchMapping("/{id}/upvote")
    public ResponseEntity addUpVoteToNews(
            @AuthenticationPrincipal Jwt tokenJWT,
            @PathVariable UUID id
    ) {
        newsService.addUpVoteToNews(tokenJWT, id);
        return ResponseEntity.noContent().build();
    }

    @Operation(hidden = true) // #spring-doc
    @GetMapping("/{id}/backup")
    public ResponseEntity<NewsBackupDto> getNewsBackup(
            @PathVariable UUID id,
            @RequestParam(name = "level") int backupLevel
    ) {
        NewsBackupDto news = newsBackupService.getNewsBackup(id, backupLevel);
        return ResponseEntity.ok(news);
    }

    @Operation(hidden = true) // #spring-doc
    @PutMapping("/{id}/backup/{backupId}/restore")
    public ResponseEntity<NewsDetailingDto> restoreNewsFromABackup(
            @PathVariable UUID id,
            @PathVariable Long backupId
    ) {
        NewsDetailingDto news = newsBackupService.restoreNewsFromABackup(id, backupId);
        return ResponseEntity.ok(news);
    }
}
