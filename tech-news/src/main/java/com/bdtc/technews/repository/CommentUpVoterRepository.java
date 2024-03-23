package com.bdtc.technews.repository;

import com.bdtc.technews.model.CommentUpVoter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentUpVoterRepository extends JpaRepository<CommentUpVoter, Long> {
    boolean existsByVoterEmailAndCommentId(String email, Long id);
}
