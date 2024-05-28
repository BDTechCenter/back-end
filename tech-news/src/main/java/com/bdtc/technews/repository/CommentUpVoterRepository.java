package com.bdtc.technews.repository;

import com.bdtc.technews.model.CommentUpVoter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentUpVoterRepository extends JpaRepository<CommentUpVoter, Long> {
    boolean existsByVoterEmailAndCommentId(String email, UUID id);

    void deleteByVoterEmailAndCommentId(String currentUserEmail, UUID id);
}
