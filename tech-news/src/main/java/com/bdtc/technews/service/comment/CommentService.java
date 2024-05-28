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
import com.bdtc.technews.service.auth.AuthorizationHandler;
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
    private AuthorizationHandler authorizationHandler;

    @Transactional
    public CommentDetailingDto createComment(Jwt tokenJWT, UUID newsId, CommentRequestDto commentRequestDto) {
        UserDto authenticatedUser = new UserDto(tokenJWT);
        LocalDateTime dateNow = dateHandler.getCurrentDateTime();
        News news = newsService.getNews(newsId);

        Comment comment = Comment.builder()
                .comment(commentRequestDto.comment())
                .author(authenticatedUser.networkUser())
                .authorEmail(authenticatedUser.networkUser())
                .publicationDate(dateNow)
                .news(news)
                .build();

        commentRepository.save(comment);

        return new CommentDetailingDto(comment, dateHandler.formatDate(comment.getPublicationDate()));
    }

    public Page<CommentDetailingWUpVoteDto> getCommentsByNewsId(Jwt tokenJWT, UUID newsId, Pageable pageable) {
        News news = newsService.getNews(newsId);
        Page<Comment> commentsPage = commentRepository.getCommentByRelevance(news, pageable);
        String currentUserEmail = new UserDto(tokenJWT).networkUser();

        return commentsPage.map(comment -> new CommentDetailingWUpVoteDto(
                comment,
                dateHandler.formatDate(comment.getPublicationDate()),
                commentUpVoterRepository.existsByVoterEmailAndCommentId(currentUserEmail, comment.getId())
        ));
    }

    @Transactional
    public void addUpVoteToComment(Jwt tokenJWT, UUID id) {
        if(!commentRepository.existsById(id)) throw new EntityNotFoundException();

        Comment comment = commentRepository.getReferenceById(id);
        String currentUserEmail = new UserDto(tokenJWT).networkUser();

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
    public CommentDetailingDto updateComment(Jwt tokenJWT, UUID id, CommentRequestDto commentRequestDto) {
        if(!commentRepository.existsById(id)) throw new EntityNotFoundException();

        Comment comment = commentRepository.getReferenceById(id);

        UserDto userDto = new UserDto(tokenJWT);
        authorizationHandler.userHasAuthorization(userDto, comment.getAuthorEmail());

        comment.updateComment(commentRequestDto.comment());

        return new CommentDetailingDto(comment, dateHandler.formatDate(comment.getPublicationDate()));
    }

    public Page<CommentDetailingDto> getCommentsByAuthor(Jwt tokenJWT, Pageable pageable) {
        String currentUserEmail = new UserDto(tokenJWT).networkUser();

        Page<Comment> commentsPage = commentRepository.getCommentByAuthor(currentUserEmail, pageable);

        return commentsPage.map(comment -> new CommentDetailingDto(
                comment,
                dateHandler.formatDate(comment.getPublicationDate()
                )));
    }

    @Transactional
    public void deleteComment(Jwt tokenJWT, UUID id) {
        if(!commentRepository.existsById(id)) throw new EntityNotFoundException();

        String currentUserEmail = new UserDto(tokenJWT).networkUser();
        Comment comment = commentRepository.getReferenceById(id);

        UserDto userDto = new UserDto(tokenJWT);
        authorizationHandler.userHasAuthorization(userDto, comment.getAuthorEmail());

        commentRepository.delete(comment);
    }
}
