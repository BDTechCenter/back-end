package com.bdtc.techradar.controller;

import com.bdtc.techradar.dto.item.*;
import com.bdtc.techradar.service.item.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Operation(
            summary = "Create radar item",
            description = """
                Rings and Expectation are represented by enums, use this notation:
                rings: HOLD, OBSERVE, TRIAL, ADOPT
                expectation: UNKNOWN("-"), ZERO_TWO("0 - 2"), TWO_FIVE("2 - 5"), FIVE_TEN("5 - 10")
                Only user with 'BDUSER' or 'ADMIN' role can create item
                """
    )
    @PostMapping()
    public ResponseEntity<ItemDetailDto> createItem(
            @AuthenticationPrincipal Jwt tokenJWT,
            @RequestBody @Valid ItemRequestDto itemRequestDto,
            UriComponentsBuilder uriBuilder
    ) {
        ItemDetailDto itemDetailDto = itemService.createItem(tokenJWT, itemRequestDto);
        var uri = uriBuilder.path("tech-radar/items/{id}").build(itemDetailDto.id());
        return ResponseEntity.created(uri).body(itemDetailDto);
    }

    @Operation(
            summary = "Create multiple items all at once",
            description = """
                Create multiple items all at once, passing a list of objects
                Only user with 'BDUSER' or 'ADMIN' role can create items
                """)
    @PostMapping("/multiple")
    public ResponseEntity<List<ItemDetailDto>> createMultipleItems(
            @AuthenticationPrincipal Jwt tokenJWT,
            @RequestBody @Valid List<ItemRequestDto> itemRequestDtos,
            UriComponentsBuilder uriBuilder
    ) {
        List<ItemDetailDto> itemDetailDtos = itemService.createMultipleItems(tokenJWT, itemRequestDtos);
        return ResponseEntity.ok().body(itemDetailDtos);
    }

    @Operation(summary = "Get active items for radar view")
    @GetMapping()
    public ResponseEntity<List<ItemPreviewDto>> getItems() {
        List<ItemPreviewDto> itemPreviewDtos = itemService.getItemsPreview();
        return ResponseEntity.ok(itemPreviewDtos);
    }

    @Operation(summary = "Get items for admin view")
    @GetMapping("/admin")
    public ResponseEntity<List<ItemAdminPreviewDto>> getItemsAdminPreview() {
        List<ItemAdminPreviewDto> itemAdminPreviewDtos = itemService.getItemsAdminPreview();
        return ResponseEntity.ok(itemAdminPreviewDtos);
    }

    @Operation(summary = "Get all items for radar view")
    @GetMapping("/all")
    public ResponseEntity<List<ItemPreviewDto>> getAllItems() {
        List<ItemPreviewDto> itemPreviewDtos = itemService.getAllItemsPreview();
        return ResponseEntity.ok(itemPreviewDtos);
    }

    @Operation(summary = "Get item detail")
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDetailDto> getItemDetail(
            @AuthenticationPrincipal Jwt tokenJWT,
            @PathVariable UUID itemId
    ) {
        return ResponseEntity.ok(itemService.getItemDetail(itemId));
    }

    @Operation(
            summary = "Update item information",
            description = """
                    Only users with 'BDUSER' or 'ADMIN' role can update item
                    """
    )
    @PatchMapping("/{itemId}")
    public ResponseEntity<ItemDetailDto> updateItem(
            @AuthenticationPrincipal Jwt tokenJWT,
            @PathVariable UUID itemId,
            @RequestBody ItemUpdateDto itemUpdateDto
    ) {
        return ResponseEntity.ok(itemService.updateItem(tokenJWT, itemId, itemUpdateDto));
    }

    @Operation(
            summary = "Publish item",
            description = """
                    Only users with 'ADMIN' role can publish item
                    """
    )
    @PatchMapping("/{itemId}/publish")
    public ResponseEntity<ItemDetailDto> publishItem(
            @AuthenticationPrincipal Jwt tokenJWT,
            @PathVariable UUID itemId
    ) {
        return ResponseEntity.ok(itemService.publishItem(tokenJWT, itemId));
    }

    @Operation(
            summary = "Archive item",
            description = """
                    Only item author or user with 'ADMIN' role can archive item
                    """
    )
    @PatchMapping("/{itemId}/archive")
    public ResponseEntity<ItemDetailDto> archiveItem(
            @AuthenticationPrincipal Jwt tokenJWT,
            @PathVariable UUID itemId
    ) {
        return ResponseEntity.ok(itemService.archiveItem(tokenJWT, itemId));
    }
}
