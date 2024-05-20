package com.bdtc.techradar.controller;

import com.bdtc.techradar.dto.item.ItemDetailDto;
import com.bdtc.techradar.dto.item.ItemPreviewDto;
import com.bdtc.techradar.dto.item.ItemRequestDto;
import com.bdtc.techradar.dto.quadrant.*;
import com.bdtc.techradar.service.quadrant.QuadrantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


@RestController
@RequestMapping("/quadrants")
public class QuadrantController {

    @Autowired
    private QuadrantService quadrantService;

    @GetMapping()
    public ResponseEntity<List<QuadrantDto>> getQuadrants() {
        List<QuadrantDto> quadrantsDtos = quadrantService.getViewQuadrants();
        return ResponseEntity.ok(quadrantsDtos);
    }

    @GetMapping("/{quadrantId}")
    public ResponseEntity<QuadrantWithItemDto> getQuadrantWithItem(
            @PathVariable String quadrantId
    ) {
        QuadrantWithItemDto quadrantWithItemDto = quadrantService.getQuadrantWithItem(quadrantId);
        return ResponseEntity.ok(quadrantWithItemDto);
    }

    @GetMapping("/{quadrantId}/items")
    public ResponseEntity<List<ItemPreviewDto>> getQuadrantItems(
            @PathVariable String quadrantId
    ) {
        List<ItemPreviewDto> itemsDtos = quadrantService.getQuadrantItems(quadrantId);
        return ResponseEntity.ok(itemsDtos);
    }

    @PostMapping()
    public ResponseEntity<QuadrantDetailDto> createQuadrant(
            @AuthenticationPrincipal Jwt tokenJWT,
            @RequestBody @Valid QuadrantRequestDto quadrantRequestDto,
            UriComponentsBuilder uriBuilder
    ) {
        QuadrantDetailDto quadrantDetailDto = quadrantService.createQuadrant(tokenJWT, quadrantRequestDto);
        var uri = uriBuilder.path("tech-radar/quadrants/{id}").build(quadrantDetailDto.id());
        return ResponseEntity.created(uri).body(quadrantDetailDto);
    }

    @PostMapping("/multiple")
    public ResponseEntity<List<QuadrantDetailDto>> createMultipleQuadrants(
            @AuthenticationPrincipal Jwt tokenJWT,
            @RequestBody @Valid List<QuadrantRequestDto> quadrantRequestDtos
    ) {
        List<QuadrantDetailDto> quadrantDetailDtos = quadrantService.createMultipleQuadrants(tokenJWT, quadrantRequestDtos);
        return ResponseEntity.ok().body(quadrantDetailDtos);
    }

    @PatchMapping("/{quadrantId}")
    public ResponseEntity<QuadrantDetailDto> updateQuandrant(
            @AuthenticationPrincipal Jwt tokenJWT,
            @PathVariable String quadrantId,
            @RequestBody QuadrantUpdateDto quadrantUpdateDto
    ) {
        return ResponseEntity.ok(quadrantService.updateQuadrant(tokenJWT, quadrantId, quadrantUpdateDto));
    }
}
