package com.bdtc.technews.controller;

import com.bdtc.technews.dto.TagDto;
import com.bdtc.technews.service.tag.TagService;
import com.bdtc.technews.service.tag.validation.creation.TagCreationValidators;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @PostMapping()
    public ResponseEntity createTag(@RequestBody @Valid TagDto tagDto, UriComponentsBuilder uriBuilder) {
        TagDto tag = tagService.createTag(tagDto);
        return ResponseEntity.ok(tag);
    }

    @GetMapping()
    public ResponseEntity getTags(@PageableDefault(sort = {"id"}) Pageable pageable) {
        Page<TagDto> tagsPage = tagService.getTagsPage(pageable);
        return ResponseEntity.ok(tagsPage);
    }

    @GetMapping("/all")
    public ResponseEntity getAllTags() {
        List<TagDto> tagsList = tagService.getAllTags();
        return ResponseEntity.ok(tagsList);
    }
}
