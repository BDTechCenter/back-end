package com.bdtc.technews.repository;

import com.bdtc.technews.model.Comment;
import com.bdtc.technews.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    Page<Comment> findAllByNews(News news, Pageable pageable);

    @Query(
            """
            SELECT c
            FROM Comment c
            WHERE c.news =:news
            ORDER BY c.upVotes DESC
            """
    )
    Page<Comment> getCommentByRelevance(@Param("news") News news, Pageable pageable);

    @Query("""
           SELECT c
           FROM Comment c
           WHERE c.authorEmail =:currentUserEmail
           ORDER BY c.publicationDate DESC
           """)
    Page<Comment> getCommentByAuthor(String currentUserEmail, Pageable pageable);
}
