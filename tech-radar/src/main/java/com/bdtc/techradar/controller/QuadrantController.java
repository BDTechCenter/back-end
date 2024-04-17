package com.bdtc.techradar.controller;

import com.bdtc.techradar.dto.QuadrantDetailDto;
import com.bdtc.techradar.dto.QuadrantDto;
import com.bdtc.techradar.service.QuadrantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


@RestController
@RequestMapping("/quadrants")
public class QuadrantController {

    @Autowired
    private QuadrantService quadrantService;

    @PostMapping("/create")
    public ResponseEntity createQuadrant(@RequestBody @Valid QuadrantDto quadrantDto, UriComponentsBuilder uriBuilder) {
        QuadrantDetailDto quadrantDetailDto = quadrantService.createQuadrant(quadrantDto);
        var uri = uriBuilder.path("tech-radar/quadrants/{id}").build(quadrantDetailDto.id());
        return ResponseEntity.created(uri).body(quadrantDetailDto);
    }

    @GetMapping()
    public ResponseEntity<List<QuadrantDto>> getQuadrants() {
        List<QuadrantDto> quadrantsDtos = quadrantService.getViewQuadrants();
        return ResponseEntity.ok(quadrantsDtos);
    }
}
