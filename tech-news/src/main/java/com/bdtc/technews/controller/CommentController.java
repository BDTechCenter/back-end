package com.bdtc.technews.controller;

import com.bdtc.technews.dto.comment.CommentDetailingDto;
import com.bdtc.technews.dto.comment.CommentDetailingWUpVoteDto;
import com.bdtc.technews.dto.comment.CommentRequestDto;
import com.bdtc.technews.service.comment.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Comments Controller", description = "Handle all comments related requests")
@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Operation(summary = "Create comment for news")
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

    @Operation(summary = "Get comment details with number of upVotes")
    @GetMapping("/{newsId}")
    public ResponseEntity<Page<CommentDetailingWUpVoteDto>> getNewsComments(
            @AuthenticationPrincipal Jwt tokenJWT,
            @PathVariable UUID newsId,
            @PageableDefault() Pageable pageable
    ) {
        Page<CommentDetailingWUpVoteDto> commentsPage = commentService.getCommentsByNewsId(tokenJWT, newsId, pageable);
        return ResponseEntity.ok(commentsPage);
    }

    @Operation(
            summary = "Update a comment",
            description = "Only the author or user with 'ADMIN' role can update a comment"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<CommentDetailingDto> updateComment(
            @AuthenticationPrincipal Jwt tokenJWT,
            @PathVariable UUID id,
            @RequestBody @Valid CommentRequestDto commentRequestDto
    ) {
        CommentDetailingDto comment = commentService.updateComment(tokenJWT, id, commentRequestDto);
        return ResponseEntity.ok(comment);
    }

    @Operation(summary = "Add or remove a upvote to measure comment relevance")
    @PatchMapping("/{id}/upvote")
    public ResponseEntity addUpVoteToComment(
            @AuthenticationPrincipal Jwt tokenJWT,
            @PathVariable UUID id
    ) {
        commentService.addUpVoteToComment(tokenJWT, id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get comment based on author (logged user)")
    @GetMapping("/author")
    public ResponseEntity<Page<CommentDetailingDto>> getCommentsByAuthor(
            @AuthenticationPrincipal Jwt tokenJWT,
            @PageableDefault() Pageable pageable
    ) {
        Page<CommentDetailingDto> commentsPage = commentService.getCommentsByAuthor(tokenJWT, pageable);
        return ResponseEntity.ok(commentsPage);
    }

    @Operation(
            summary = "Delete a comment",
            description = "Only the author or user with 'ADMIN' role can delete a comment"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity deleteComment(
            @AuthenticationPrincipal Jwt tokenJWT,
            @PathVariable UUID id
    ) {
        commentService.deleteComment(tokenJWT, id);
        return ResponseEntity.noContent().build();
    }
}
