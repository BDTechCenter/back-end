package com.bdtc.technews.repository;

import com.bdtc.technews.model.NewsUpVoter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NewsUpVoterRepository extends JpaRepository<NewsUpVoter, Long> {
    boolean existsByVoterEmailAndNewsId(String email, UUID id);
}
