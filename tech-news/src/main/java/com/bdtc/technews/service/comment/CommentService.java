package com.bdtc.technews.service.comment;

import com.bdtc.technews.dto.CommentDetailingDto;
import com.bdtc.technews.dto.CommentRequestDto;
import com.bdtc.technews.http.auth.service.AuthClientService;
import com.bdtc.technews.infra.exception.validation.BusinessRuleException;
import com.bdtc.technews.model.Comment;
import com.bdtc.technews.model.CommentUpVoter;
import com.bdtc.technews.model.News;
import com.bdtc.technews.repository.CommentRepository;
import com.bdtc.technews.repository.CommentUpVoterRepository;
import com.bdtc.technews.service.news.NewsService;
import com.bdtc.technews.service.news.utils.DateHandler;
import jakarta.persistence.EntityNotFoundException;
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
    private CommentUpVoterRepository commentUpVoterRepository;

    @Autowired
    private NewsService newsService;

    @Autowired
    private DateHandler dateHandler;

    @Autowired
    private AuthClientService authService;

    @Transactional
    public CommentDetailingDto createComment(UUID newsId, CommentRequestDto commentRequestDto) {
        Comment comment = new Comment(commentRequestDto);
        LocalDateTime date = dateHandler.getCurrentDateTime();
        News news = newsService.getNews(newsId);

        comment.setPublicationDate(date);
        comment.setNews(news);
        commentRepository.save(comment);

        return new CommentDetailingDto(comment, dateHandler.formatDate(comment.getPublicationDate()));
    }

    public Page<CommentDetailingDto> getCommentsByNewsId(UUID newsId, Pageable pageable) {
        News news = newsService.getNews(newsId);
        Page<Comment> commentsPage = commentRepository.getCommentByRelevance(news, pageable);
        return commentsPage.map(comment -> new CommentDetailingDto(
                comment,
                dateHandler.formatDate(comment.getPublicationDate())
        ));
    }

    @Transactional
    public void addUpVoteToComment(String tokenJWT, Long id) {
        if(!commentRepository.existsById(id)) throw new EntityNotFoundException();

        Comment comment = commentRepository.getReferenceById(id);
        String currentUserEmail = authService.getNtwUser(tokenJWT);

        if(commentUpVoterRepository.existsByVoterEmailAndCommentId(currentUserEmail, comment.getId())) {
            throw new BusinessRuleException("You cant upVote twice!");
        }

        CommentUpVoter commentUpVoter = new CommentUpVoter(currentUserEmail, comment);
        comment.getCommentUpVoters().add(commentUpVoter);
        commentUpVoterRepository.save(commentUpVoter);

        comment.addUpVote();
    }
}
