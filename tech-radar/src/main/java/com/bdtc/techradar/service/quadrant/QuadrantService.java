package com.bdtc.techradar.service.quadrant;

import com.bdtc.techradar.constant.QuadrantEnum;
import com.bdtc.techradar.dto.quadrant.QuadrantDetailDto;
import com.bdtc.techradar.dto.quadrant.QuadrantDto;
import com.bdtc.techradar.dto.quadrant.QuadrantRequestDto;
import com.bdtc.techradar.dto.quadrant.QuadrantUpdateDto;
import com.bdtc.techradar.dto.user.UserDto;
import com.bdtc.techradar.infra.exception.validation.QuadrantAlreadyExistsException;
import com.bdtc.techradar.model.Quadrant;
import com.bdtc.techradar.repository.QuadrantRepository;
import com.bdtc.techradar.service.auth.AuthHandler;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuadrantService {

    @Autowired
    private QuadrantRepository quadrantRepository;

    @Autowired
    private AuthHandler authHandler;

    public List<QuadrantDto> getViewQuadrants() {
        List<QuadrantDto> quadrantViewDtos = new ArrayList<>();
        List<Quadrant> quadrantsList = quadrantRepository.findAll();

        for (Quadrant quadrant : quadrantsList) {
            quadrantViewDtos.add(new QuadrantDto(quadrant));
        }
        return quadrantViewDtos;
    }

    @Transactional
    public QuadrantDetailDto createQuadrant(Jwt tokenJWT, QuadrantRequestDto quadrantRequestDto) {
        authHandler.validateUserIsAdmin(tokenJWT);

        if (!quadrantRepository.existsById(quadrantRequestDto.quadrant().getTitle())) {
            Quadrant quadrant = new Quadrant(quadrantRequestDto);
            quadrantRepository.save(quadrant);
            return new QuadrantDetailDto(quadrant);
        }
        throw new QuadrantAlreadyExistsException();
    }

    public Quadrant getQuadrant(QuadrantEnum quadrantEnum) {
        return quadrantRepository.getReferenceById(quadrantEnum.getTitle());
    }

    @Transactional
    public QuadrantDetailDto updateQuadrant(Jwt tokenJWT, String quadrantId, QuadrantUpdateDto quadrantUpdateDto) {
        authHandler.validateUserIsAdmin(tokenJWT);

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
