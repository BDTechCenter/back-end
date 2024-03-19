package com.bdtc.technews.repository;

import com.bdtc.technews.model.Comment;
import com.bdtc.technews.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByNews(News news, Pageable pageable);
}
