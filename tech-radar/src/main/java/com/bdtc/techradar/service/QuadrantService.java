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
    public QuadrantDetailDto createQuadrant(QuadrantRequestDto quadrantRequestDto) throws Exception {
        if (!quadrantRepository.existsById(quadrantRequestDto.quadrant().getTitle())) {
            Quadrant quadrant = new Quadrant(quadrantRequestDto);
            quadrantRepository.save(quadrant);
            return new QuadrantDetailDto(quadrant);
        }
        throw new Exception("Exception message");
    }

    public Quadrant getQuadrant(QuadrantEnum quadrantEnum) {
        return quadrantRepository.findByTitle(quadrantEnum.getTitle());
    }

    @Transactional
    public QuadrantDetailDto updateQuadrant(String quadrantId, QuadrantUpdateDto quadrantUpdateDto) {
        Quadrant quadrant = quadrantRepository.getReferenceById(quadrantId);

        if (quadrantUpdateDto.name() != null) quadrant.setName(quadrantUpdateDto.name());
        if (quadrantUpdateDto.title() != null) quadrant.setTitle(quadrantUpdateDto.title());
        if (quadrantUpdateDto.color() != null) quadrant.setColor(quadrantUpdateDto.color());
        if (quadrantUpdateDto.txtColor() != null) quadrant.setTxtColor(quadrantUpdateDto.txtColor());
        if (quadrantUpdateDto.position() != null) quadrant.setPosition(quadrantUpdateDto.position());
        if (quadrantUpdateDto.description() != null) quadrant.setDescription(quadrantUpdateDto.description());

        return new QuadrantDetailDto(quadrant);
    }
}
