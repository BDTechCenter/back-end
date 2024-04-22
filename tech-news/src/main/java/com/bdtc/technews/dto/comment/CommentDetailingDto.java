package com.bdtc.technews.dto.comment;

import com.bdtc.technews.model.Comment;

import java.util.UUID;

public record CommentDetailingDto(
        Long id,
        UUID newsId,
        String author,
        String publicationDate,
        String comment,
        int upVotes
) {
    public CommentDetailingDto(Comment comment, String publicationDate) {
        this(
                comment.getId(),
                comment.getNews().getId(),
                comment.getAuthor(),
                publicationDate, comment.getComment(),
                comment.getUpVotes()
        );
    }
}
