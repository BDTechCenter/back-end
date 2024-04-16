package com.bdtc.techradar.service;

import com.bdtc.techradar.dto.QuadrantViewDto;
import com.bdtc.techradar.model.Quadrant;
import com.bdtc.techradar.repository.QuadrantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuadrantService {

    @Autowired
    private QuadrantRepository quadrantRepository;

    public List<QuadrantViewDto> getViewQuadrants() {
        List<QuadrantViewDto> quadrantViewDtos = new ArrayList<>();
        List<Quadrant> quadrantsList = quadrantRepository.findAll();
        for (Quadrant quadrant : quadrantsList) {
            quadrantViewDtos.add(new QuadrantViewDto(quadrant));
        }
        return quadrantViewDtos;
    }
}
