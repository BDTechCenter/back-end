package com.bdtc.technews.service.comment;

import com.bdtc.technews.dto.CommentDetailingDto;
import com.bdtc.technews.dto.CommentRequestDto;
import com.bdtc.technews.model.Comment;
import com.bdtc.technews.model.News;
import com.bdtc.technews.repository.CommentRepository;
import com.bdtc.technews.service.news.NewsService;
import com.bdtc.technews.service.news.utils.DateHandler;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private NewsService newsService;

    @Autowired
    private DateHandler dateHandler;

    @Transactional
    public CommentDetailingDto createComment(UUID newsId, CommentRequestDto commentRequestDto) {
        Comment comment = new Comment(commentRequestDto);
        LocalDateTime date = dateHandler.getCurrentDateTime();

        comment.setPublicationDate(date);
        comment.setNews(newsService.getNews(newsId));
        commentRepository.save(comment);

        return new CommentDetailingDto(comment, dateHandler.formatDate(comment.getPublicationDate()));
    }

    public Page<CommentDetailingDto> getCommentsByNewsId(UUID newsId, Pageable pageable) {
        News news = newsService.getNews(newsId);
        Page<Comment> commentsPage = commentRepository.findAllByNews(news, pageable);
        return commentsPage.map(comment -> new CommentDetailingDto(
                comment,
                dateHandler.formatDate(comment.getPublicationDate())
        ));
    }


}
