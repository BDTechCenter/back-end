package com.bdtc.technews.service.comment;

import com.bdtc.technews.dto.CommentDetailingDto;
import com.bdtc.technews.dto.CommentRequestDto;
import com.bdtc.technews.model.Comment;
import com.bdtc.technews.model.News;
import com.bdtc.technews.repository.CommentRepository;
import com.bdtc.technews.service.news.NewsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private NewsService newsService;

    @Transactional
    public CommentDetailingDto createComment(UUID newsId, CommentRequestDto commentRequestDto) {
        Comment comment = new Comment(commentRequestDto);
        comment.setNews(newsService.getNews(newsId));
        commentRepository.save(comment);
        return new CommentDetailingDto(comment);
    }

    public Page<CommentDetailingDto> getCommentsByNewsId(UUID newsId, Pageable pageable) {
        News news = newsService.getNews(newsId);
        Page<Comment> commentsPage = commentRepository.findAllByNews(news, pageable);
        return commentsPage.map(comment -> new CommentDetailingDto(comment));
    }


}
