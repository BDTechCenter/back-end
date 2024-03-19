package com.bdtc.technews.dto;

import com.bdtc.technews.model.Comment;

import java.util.UUID;

public record CommentDetailingDto(
        Long id,
        UUID newsId,
        String author,
        String body
) {
    public CommentDetailingDto(Comment comment) {
        this(comment.getId(), comment.getNews().getId(), comment.getAuthor(), comment.getBody());
    }
}
