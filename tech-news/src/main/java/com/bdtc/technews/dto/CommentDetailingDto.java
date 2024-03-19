package com.bdtc.technews.dto;

import com.bdtc.technews.model.Comment;

public record CommentDetailingDto(
        Long id,
        String author,
        String body
) {
    public CommentDetailingDto(Comment comment) {
        this(comment.getId(), comment.getAuthor(), comment.getBody());
    }
}
