package com.bdtc.technews.controller;

import com.bdtc.technews.dto.comment.CommentDetailingDto;
import com.bdtc.technews.dto.comment.CommentDetailingWUpVoteDto;
import com.bdtc.technews.dto.comment.CommentRequestDto;
import com.bdtc.technews.service.comment.CommentService;
import jakarta.transaction.Transactional;
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

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/{newsId}")
    @Transactional
    public ResponseEntity<CommentDetailingDto> createComment(
            @AuthenticationPrincipal Jwt tokenJWT,
            @PathVariable UUID newsId,
            @RequestBody @Valid CommentRequestDto commentRequestDto,
            UriComponentsBuilder uriBuilder
    ) {
        CommentDetailingDto comment = commentService.createComment(tokenJWT, newsId, commentRequestDto);
        var uri = uriBuilder.path("tech-news/comments/{id}").build(comment.id());
        return ResponseEntity.created(uri).body(comment);
    }

    @GetMapping("/{newsId}")
    public ResponseEntity<Page<CommentDetailingWUpVoteDto>> getNewsComments(
            @AuthenticationPrincipal Jwt tokenJWT,
            @PathVariable UUID newsId,
            @PageableDefault() Pageable pageable
    ) {
        Page<CommentDetailingWUpVoteDto> commentsPage = commentService.getCommentsByNewsId(tokenJWT, newsId, pageable);
        return ResponseEntity.ok(commentsPage);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommentDetailingDto> updateComment(
            @AuthenticationPrincipal Jwt tokenJWT,
            @PathVariable Long id,
            @RequestBody @Valid CommentRequestDto commentRequestDto
    ) {
        CommentDetailingDto comment = commentService.updateComment(tokenJWT, id, commentRequestDto);
        return ResponseEntity.ok(comment);
    }

    @PatchMapping("/{id}/upvote")
    public ResponseEntity addUpVoteToComment(
            @AuthenticationPrincipal Jwt tokenJWT,
            @PathVariable Long id
    ) {
        commentService.addUpVoteToComment(tokenJWT, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/author")
    public ResponseEntity<Page<CommentDetailingDto>> getCommentsByAuthor(
            @AuthenticationPrincipal Jwt tokenJWT,
            @PageableDefault() Pageable pageable
    ) {
        Page<CommentDetailingDto> commentsPage = commentService.getCommentsByAuthor(tokenJWT, pageable);
        return ResponseEntity.ok(commentsPage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteComment(
            @AuthenticationPrincipal Jwt tokenJWT,
            @PathVariable Long id
    ) {
        commentService.deleteComment(tokenJWT, id);
        return ResponseEntity.noContent().build();
    }
}
