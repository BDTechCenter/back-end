package com.bdtc.technews.service.comment;

import com.bdtc.technews.dto.comment.CommentDetailingDto;
import com.bdtc.technews.dto.comment.CommentDetailingWUpVoteDto;
import com.bdtc.technews.dto.comment.CommentRequestDto;
import com.bdtc.technews.dto.user.UserDto;
import com.bdtc.technews.infra.exception.validation.PermissionException;
import com.bdtc.technews.model.Comment;
import com.bdtc.technews.model.CommentUpVoter;
import com.bdtc.technews.model.News;
import com.bdtc.technews.repository.CommentRepository;
import com.bdtc.technews.repository.CommentUpVoterRepository;
import com.bdtc.technews.service.auth.UserHandler;
import com.bdtc.technews.service.news.NewsService;
import com.bdtc.technews.service.news.utils.DateHandler;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.jwt.Jwt;
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
    private UserHandler userHandler;

    @Transactional
    public CommentDetailingDto createComment(Jwt tokenJWT, UUID newsId, CommentRequestDto commentRequestDto) {
        Comment comment = new Comment(commentRequestDto);
        UserDto authenticatedUser = userHandler.getUserByTokenJWT(tokenJWT);
        LocalDateTime date = dateHandler.getCurrentDateTime();
        News news = newsService.getNews(newsId);

        comment.setAuthorEmail(authenticatedUser.networkUser());
        comment.setAuthor(authenticatedUser.username());
        comment.setPublicationDate(date);
        comment.setNews(news);
        commentRepository.save(comment);

        return new CommentDetailingDto(comment, dateHandler.formatDate(comment.getPublicationDate()));
    }

    public Page<CommentDetailingWUpVoteDto> getCommentsByNewsId(Jwt tokenJWT, UUID newsId, Pageable pageable) {
        News news = newsService.getNews(newsId);
        Page<Comment> commentsPage = commentRepository.getCommentByRelevance(news, pageable);
        String currentUserEmail = userHandler.getUserByTokenJWT(tokenJWT).networkUser();

        return commentsPage.map(comment -> new CommentDetailingWUpVoteDto(
                comment,
                dateHandler.formatDate(comment.getPublicationDate()),
                commentUpVoterRepository.existsByVoterEmailAndCommentId(currentUserEmail, comment.getId())
        ));
    }

    @Transactional
    public void addUpVoteToComment(Jwt tokenJWT, Long id) {
        if(!commentRepository.existsById(id)) throw new EntityNotFoundException();

        Comment comment = commentRepository.getReferenceById(id);
        String currentUserEmail = userHandler.getUserByTokenJWT(tokenJWT).networkUser();

        if(commentUpVoterRepository.existsByVoterEmailAndCommentId(currentUserEmail, comment.getId())) {
            commentUpVoterRepository.deleteByVoterEmailAndCommentId(currentUserEmail, id);
            comment.removeUpvote();
        }else {
            CommentUpVoter commentUpVoter = new CommentUpVoter(currentUserEmail, comment);
            comment.getCommentUpVoters().add(commentUpVoter);
            commentUpVoterRepository.save(commentUpVoter);
            comment.addUpVote();
        }
    }

    @Transactional
    public CommentDetailingDto updateComment(Jwt tokenJWT, Long id, CommentRequestDto commentRequestDto) {
        if(!commentRepository.existsById(id)) throw new EntityNotFoundException();

        Comment comment = commentRepository.getReferenceById(id);
        String currentUserEmail = userHandler.getUserByTokenJWT(tokenJWT).networkUser();

        if(!currentUserEmail.equals(comment.getAuthorEmail())) throw new PermissionException();

        comment.updateComment(commentRequestDto.comment());

        return new CommentDetailingDto(comment, dateHandler.formatDate(comment.getPublicationDate()));
    }

    public Page<CommentDetailingDto> getCommentsByAuthor(Jwt tokenJWT, Pageable pageable) {
        String currentUserEmail = userHandler.getUserByTokenJWT(tokenJWT).networkUser();

        Page<Comment> commentsPage = commentRepository.getCommentByAuthor(currentUserEmail, pageable);

        return commentsPage.map(comment -> new CommentDetailingDto(
                comment,
                dateHandler.formatDate(comment.getPublicationDate()
                )));
    }

    @Transactional
    public void deleteComment(Jwt tokenJWT, Long id) {
        if(!commentRepository.existsById(id)) throw new EntityNotFoundException();

        String currentUserEmail = userHandler.getUserByTokenJWT(tokenJWT).networkUser();
        Comment comment = commentRepository.getReferenceById(id);

        if(!currentUserEmail.equals(comment.getAuthorEmail())) throw new PermissionException();

        commentRepository.delete(comment);
    }
}
