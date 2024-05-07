package com.bdtc.techradar.repository;

import com.bdtc.techradar.model.Item;
import com.bdtc.techradar.model.Quadrant;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuadrantRepository extends JpaRepository<Quadrant, String> {

    @Query(
            """
            SELECT i
            FROM Item i
            WHERE i.quadrant =:quadrant
            """
    )
    List<Item> findAllRelatedItemsByQuadrantId(@Param("quadrant") Quadrant quadrant);
}
