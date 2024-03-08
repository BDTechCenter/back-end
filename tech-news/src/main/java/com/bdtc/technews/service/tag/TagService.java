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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        Page<Tag> tags = tagRepository.findAll(pageable);
        return tags.map(TagDto::new);
    }

    public List<TagDto> getAllTags() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream().map(TagDto::new).toList();
    }

    public Set<Tag> getTagSet(Set<String> tagsStringList) {
        Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tagsStringList) {
            Tag tagEntity = tagRepository.findByName(tagName);
            if(tagEntity == null) {
                tagEntity = tagRepository.save(new Tag(tagName));
            }
            tagSet.add(tagEntity);
        }
        return tagSet;
    }
}
