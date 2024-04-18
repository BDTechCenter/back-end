package com.bdtc.techradar.repository;

import com.bdtc.techradar.model.Quadrant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuadrantRepository extends JpaRepository<Quadrant, String> {
    Quadrant findByTitle(String title);
}
