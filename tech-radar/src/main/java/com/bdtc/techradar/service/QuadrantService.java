package com.bdtc.techradar.service;

import com.bdtc.techradar.constant.QuadrantEnum;
import com.bdtc.techradar.dto.quadrant.QuadrantDetailDto;
import com.bdtc.techradar.dto.quadrant.QuadrantDto;
import com.bdtc.techradar.dto.quadrant.QuadrantRequestDto;
import com.bdtc.techradar.dto.quadrant.QuadrantUpdateDto;
import com.bdtc.techradar.model.Quadrant;
import com.bdtc.techradar.repository.QuadrantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class QuadrantService {

    @Autowired
    private QuadrantRepository quadrantRepository;

    public List<QuadrantDto> getViewQuadrants() {
        List<QuadrantDto> quadrantViewDtos = new ArrayList<>();
        List<Quadrant> quadrantsList = quadrantRepository.findAll();

        for (Quadrant quadrant : quadrantsList) {
            quadrantViewDtos.add(new QuadrantDto(quadrant));
        }
        return quadrantViewDtos;
    }

    @Transactional
    public QuadrantDetailDto createQuadrant(QuadrantRequestDto quadrantRequestDto) {
        Quadrant quadrant = new Quadrant(quadrantRequestDto);
        quadrantRepository.save(quadrant);
        return new QuadrantDetailDto(quadrant);
    }

    public Quadrant getQuadrant(QuadrantEnum quadrantEnum) {
        return quadrantRepository.findByTitle(quadrantEnum.getTitle());
    }

    @Transactional
    public QuadrantDetailDto updateQuadrant(QuadrantEnum quadrantEnum, QuadrantUpdateDto quadrantUpdateDto) {
        Quadrant quadrant = getQuadrant(quadrantEnum);

        return new QuadrantDetailDto(quadrant);
    }
}
