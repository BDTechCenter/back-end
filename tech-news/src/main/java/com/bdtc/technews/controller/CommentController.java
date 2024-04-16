package com.bdtc.technews.controller;

import com.bdtc.technews.dto.CommentDetailingDto;
import com.bdtc.technews.dto.CommentDetailingWUpVoteDto;
import com.bdtc.technews.dto.CommentRequestDto;
import com.bdtc.technews.service.comment.CommentService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/{newsId}")
    @Transactional
    public ResponseEntity createComment(@RequestHeader("Authorization") String tokenJWT, @PathVariable UUID newsId, @RequestBody @Valid CommentRequestDto commentRequestDto) {
        CommentDetailingDto comment = commentService.createComment(tokenJWT, newsId, commentRequestDto);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/{newsId}")
    public ResponseEntity getNewsComments(@RequestHeader("Authorization") String tokenJWT, @PathVariable UUID newsId, @PageableDefault() Pageable pageable) {
        Page<CommentDetailingWUpVoteDto> commentsPage = commentService.getCommentsByNewsId(tokenJWT, newsId, pageable);
        return ResponseEntity.ok(commentsPage);
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateComment(@RequestHeader("Authorization") String tokenJWT, @PathVariable Long id, @RequestBody @Valid CommentRequestDto commentRequestDto) {
        CommentDetailingDto comment = commentService.updateComment(tokenJWT, id, commentRequestDto);
        return ResponseEntity.ok(comment);
    }

    @PatchMapping("/{id}/upvote")
    public ResponseEntity addUpVoteToComment(@RequestHeader("Authorization") String tokenJWT, @PathVariable Long id) {
        commentService.addUpVoteToComment(tokenJWT, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/author")
    public ResponseEntity getCommentsByAuthor(@RequestHeader("Authorization") String tokenJWT, @PageableDefault() Pageable pageable) {
        Page<CommentDetailingDto> commentsPage = commentService.getCommentsByAuthor(tokenJWT, pageable);
        return ResponseEntity.ok(commentsPage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteComment(@RequestHeader("Authorization") String tokenJWT, @PathVariable Long id) {
        commentService.deleteComment(tokenJWT, id);
        return ResponseEntity.ok().build();
    }
}
