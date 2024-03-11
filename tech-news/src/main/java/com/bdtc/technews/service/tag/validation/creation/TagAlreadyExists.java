package com.bdtc.technews.service.tag.validation.creation;

import com.bdtc.technews.dto.TagDto;
import com.bdtc.technews.repository.TagRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TagAlreadyExists implements TagCreationValidators{

    @Autowired
    private TagRepository tagRepository;

    @Override
    public void validate(TagDto tagDto) {
        var alreadyExists = tagRepository.existsByName(tagDto.tag());
        if(alreadyExists) throw new ValidationException(String.format("Tag '%s' already exists!", tagDto.tag()));
    }
}
