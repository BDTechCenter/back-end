package com.bdtc.techradar.controller;

import com.bdtc.techradar.dto.QuadrantViewDto;
import com.bdtc.techradar.service.QuadrantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/quadrants")
public class QuadrantController {

    @Autowired
    private QuadrantService quadrantService;

    @GetMapping()
    public ResponseEntity<List<QuadrantViewDto>> getQuadrants() {
        List<QuadrantViewDto> quadrantsDtos = quadrantService.getViewQuadrants();
        return ResponseEntity.ok(quadrantsDtos);
    }
}
