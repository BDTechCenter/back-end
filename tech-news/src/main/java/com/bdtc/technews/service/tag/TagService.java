package com.bdtc.technews.service.tag;

import com.bdtc.technews.dto.TagDto;
import com.bdtc.technews.model.Tag;
import com.bdtc.technews.repository.TagRepository;
import com.bdtc.technews.service.tag.validation.creation.TagCreationValidators;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private List<TagCreationValidators> tagCreationValidators;

    @Transactional
    public TagDto createTag(TagDto tagDto) {
        tagCreationValidators.forEach(v -> v.validate(tagDto));
        tagRepository.save(new Tag(tagDto));
        return tagDto;
    }

    public Page<TagDto> getTagsPage(Pageable pageable) {
        var tags = tagRepository.findAll(pageable);
        return tags.map(TagDto::new);
    }

    public List<TagDto> getAllTags() {
        var tags = tagRepository.findAll();
        return tags.stream().map(TagDto::new).toList();
    }

}
