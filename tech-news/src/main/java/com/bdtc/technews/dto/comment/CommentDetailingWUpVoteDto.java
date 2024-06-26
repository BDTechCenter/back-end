package com.bdtc.technews.dto.comment;

import com.bdtc.technews.model.Comment;

import java.util.UUID;

public record CommentDetailingWUpVoteDto(
        UUID id,
        UUID newsId,
        String author,
        String publicationDate,
        String comment,
        int upVotes,
        boolean alreadyUpVoted
) {

    public CommentDetailingWUpVoteDto(Comment comment, String publicationDate, boolean alreadyUpVoted) {
        this(
                comment.getId(),
                comment.getNews().getId(),
                comment.getAuthor(),
                publicationDate, comment.getComment(),
                comment.getUpVotes(),
                alreadyUpVoted
        );
    }
}
