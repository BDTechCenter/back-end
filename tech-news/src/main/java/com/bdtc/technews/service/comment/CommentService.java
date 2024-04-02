package com.bdtc.technews.service.comment;

import com.bdtc.technews.dto.CommentDetailingDto;
import com.bdtc.technews.dto.CommentDetailingWUpVoteDto;
import com.bdtc.technews.dto.CommentRequestDto;
import com.bdtc.technews.http.auth.service.AuthClientService;
import com.bdtc.technews.infra.exception.validation.AlreadyUpVotedException;
import com.bdtc.technews.infra.exception.validation.PermissionException;
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
    public CommentDetailingDto createComment(String tokenJWT, UUID newsId, CommentRequestDto commentRequestDto) {
        Comment comment = new Comment(commentRequestDto);
        String currentUserEmail = authService.getNtwUser(tokenJWT);
        LocalDateTime date = dateHandler.getCurrentDateTime();
        News news = newsService.getNews(newsId);

        comment.setAuthor(currentUserEmail);
        comment.setPublicationDate(date);
        comment.setNews(news);
        commentRepository.save(comment);

        return new CommentDetailingDto(comment, dateHandler.formatDate(comment.getPublicationDate()));
    }

    public Page<CommentDetailingWUpVoteDto> getCommentsByNewsId(String tokenJWT, UUID newsId, Pageable pageable) {
        News news = newsService.getNews(newsId);
        Page<Comment> commentsPage = commentRepository.getCommentByRelevance(news, pageable);
        String currentUserEmail = authService.getNtwUser(tokenJWT);

        return commentsPage.map(comment -> new CommentDetailingWUpVoteDto(
                comment,
                dateHandler.formatDate(comment.getPublicationDate()),
                commentUpVoterRepository.existsByVoterEmailAndCommentId(currentUserEmail, comment.getId())
        ));
    }

    @Transactional
    public void addUpVoteToComment(String tokenJWT, Long id) {
        if(!commentRepository.existsById(id)) throw new EntityNotFoundException();

        Comment comment = commentRepository.getReferenceById(id);
        String currentUserEmail = authService.getNtwUser(tokenJWT);

        if(commentUpVoterRepository.existsByVoterEmailAndCommentId(currentUserEmail, comment.getId())) {
            throw new AlreadyUpVotedException();
        }

        CommentUpVoter commentUpVoter = new CommentUpVoter(currentUserEmail, comment);
        comment.getCommentUpVoters().add(commentUpVoter);
        commentUpVoterRepository.save(commentUpVoter);

        comment.addUpVote();
    }

    @Transactional
    public CommentDetailingDto updateComment(String tokenJWT, Long id, CommentRequestDto commentRequestDto) {
        if(!commentRepository.existsById(id)) throw new EntityNotFoundException();

        Comment comment = commentRepository.getReferenceById(id);
        String currentUserEmail = authService.getNtwUser(tokenJWT);

        if(!currentUserEmail.equals(comment.getAuthor())) throw new PermissionException();

        comment.updateComment(commentRequestDto.comment());

        return new CommentDetailingDto(comment, dateHandler.formatDate(comment.getPublicationDate()));
    }
}
