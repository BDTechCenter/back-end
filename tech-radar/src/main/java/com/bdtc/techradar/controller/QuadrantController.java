package com.bdtc.techradar.controller;

import com.bdtc.techradar.constant.QuadrantEnum;
import com.bdtc.techradar.dto.quadrant.QuadrantDetailDto;
import com.bdtc.techradar.dto.quadrant.QuadrantDto;
import com.bdtc.techradar.dto.quadrant.QuadrantRequestDto;
import com.bdtc.techradar.dto.quadrant.QuadrantUpdateDto;
import com.bdtc.techradar.service.QuadrantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/quadrants")
public class QuadrantController {

    @Autowired
    private QuadrantService quadrantService;

    @PostMapping("/create")
    public ResponseEntity<QuadrantDetailDto> createQuadrant(
            @RequestBody @Valid QuadrantRequestDto quadrantRequestDto,
            UriComponentsBuilder uriBuilder
    ) throws Exception {
        QuadrantDetailDto quadrantDetailDto = quadrantService.createQuadrant(quadrantRequestDto);
        var uri = uriBuilder.path("tech-radar/quadrants/{id}").build(quadrantDetailDto.id());
        return ResponseEntity.created(uri).body(quadrantDetailDto);
    }

    @GetMapping()
    public ResponseEntity<List<QuadrantDto>> getQuadrants() {
        List<QuadrantDto> quadrantsDtos = quadrantService.getViewQuadrants();
        return ResponseEntity.ok(quadrantsDtos);
    }

    @PatchMapping("/{quadrant}")
    public ResponseEntity<QuadrantDetailDto> updateQuandrant(@PathVariable QuadrantEnum quadrant, @RequestBody QuadrantUpdateDto quadrantUpdateDto) {
        return ResponseEntity.ok(quadrantService.updateQuadrant(quadrant, quadrantUpdateDto));
    }
}
