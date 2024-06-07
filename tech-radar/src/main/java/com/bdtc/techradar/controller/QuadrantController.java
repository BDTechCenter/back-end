package com.bdtc.techradar.controller;

import com.bdtc.techradar.dto.item.ItemDetailDto;
import com.bdtc.techradar.dto.item.ItemPreviewDto;
import com.bdtc.techradar.dto.item.ItemRequestDto;
import com.bdtc.techradar.dto.quadrant.*;
import com.bdtc.techradar.service.quadrant.QuadrantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Tag(name = "Quadrants Controller", description = "Handle all quadrants related requests")  // spring-doc
@RestController
@RequestMapping("/quadrants")
public class QuadrantController {

    @Autowired
    private QuadrantService quadrantService;

    @Operation(summary = "Get quadrants for radar view") // #spring-doc
    @GetMapping()
    public ResponseEntity<List<QuadrantDto>> getQuadrants() {
        List<QuadrantDto> quadrantsDtos = quadrantService.getViewQuadrants();
        return ResponseEntity.ok(quadrantsDtos);
    }

    @Operation(summary = "Get quadrant by id") // #spring-doc
    @GetMapping("/{quadrantId}")
    public ResponseEntity<QuadrantWithItemDto> getQuadrantWithItem(
            @PathVariable String quadrantId
    ) {
        QuadrantWithItemDto quadrantWithItemDto = quadrantService.getQuadrantWithItem(quadrantId);
        return ResponseEntity.ok(quadrantWithItemDto);
    }

    @Operation(summary = "Get all items from a specific quadrant")
    @GetMapping("/{quadrantId}/items")
    public ResponseEntity<List<ItemPreviewDto>> getQuadrantItems(
            @PathVariable String quadrantId
    ) {
        List<ItemPreviewDto> itemsDtos = quadrantService.getQuadrantItems(quadrantId);
        return ResponseEntity.ok(itemsDtos);
    }

    @Operation(
            summary = "Create a quadrant",
            description = """
                Quadrants are represented by enums, use this notation:
                Only one of each: FIRST_QUADRANT, SECOND_QUADRANT, THIRD_QUADRANT, FOURTH_QUADRANT
                
                Only users with 'ADMIN' role can create quadrant
                """
    )
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

    @Operation(
            summary = "Create multiple quadrants",
            description = """
                Create multiple quadrants all at once, passing a list of objects
                Only users with 'ADMIN' role can create quadrants
                """
    )
    @PostMapping("/multiple")
    public ResponseEntity<List<QuadrantDetailDto>> createMultipleQuadrants(
            @AuthenticationPrincipal Jwt tokenJWT,
            @RequestBody @Valid List<QuadrantRequestDto> quadrantRequestDtos
    ) {
        List<QuadrantDetailDto> quadrantDetailDtos = quadrantService.createMultipleQuadrants(tokenJWT, quadrantRequestDtos);
        return ResponseEntity.ok().body(quadrantDetailDtos);
    }

    @Operation(
            summary = "Update quadrant information",
            description = "Only users with 'ADMIN' role can update quadrant"
    )
    @PatchMapping("/{quadrantId}")
    public ResponseEntity<QuadrantDetailDto> updateQuandrant(
            @AuthenticationPrincipal Jwt tokenJWT,
            @PathVariable String quadrantId,
            @RequestBody QuadrantUpdateDto quadrantUpdateDto
    ) {
        return ResponseEntity.ok(quadrantService.updateQuadrant(tokenJWT, quadrantId, quadrantUpdateDto));
    }
}
