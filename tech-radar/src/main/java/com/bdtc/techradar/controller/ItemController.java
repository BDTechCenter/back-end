package com.bdtc.techradar.controller;

import com.bdtc.techradar.dto.item.ItemDetailDto;
import com.bdtc.techradar.dto.item.ItemPreviewDto;
import com.bdtc.techradar.dto.item.ItemRequestDto;
import com.bdtc.techradar.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/create")
    public ResponseEntity<ItemDetailDto> createItem(@RequestBody @Valid ItemRequestDto itemRequestDto, UriComponentsBuilder uriBuilder) {
        ItemDetailDto itemDetailDto = itemService.createItem(itemRequestDto);
        var uri = uriBuilder.path("tech-radar/items/{id}").build(itemDetailDto.id());
        return ResponseEntity.created(uri).body(itemDetailDto);
    }
    @GetMapping()
    public ResponseEntity<List<ItemPreviewDto>> getQuadrants() {
        List<ItemPreviewDto> itemPreviewDtos = itemService.getItemsPreview();
        return ResponseEntity.ok(itemPreviewDtos);
    }

    @GetMapping("/{itemId}/detail")
    public ResponseEntity<ItemDetailDto> getItemDetail(@PathVariable UUID itemId) {
        return ResponseEntity.ok(itemService.getItemDetail(itemId));
    }
}
