package com.bdtc.techradar.service;

import com.bdtc.techradar.dto.item.ItemDetailDto;
import com.bdtc.techradar.dto.item.ItemPreviewDto;
import com.bdtc.techradar.dto.item.ItemRequestDto;
import com.bdtc.techradar.dto.item.ItemUpdateDto;
import com.bdtc.techradar.model.Item;
import com.bdtc.techradar.model.Quadrant;
import com.bdtc.techradar.repository.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private QuadrantService quadrantService;

    public List<ItemPreviewDto> getItemsPreview() {
        List<ItemPreviewDto> itemPreviewDtos = new ArrayList<>();
        List<Item> items = itemRepository.findAllByIsActiveTrue();

        for (Item item : items) {
            itemPreviewDtos.add(new ItemPreviewDto(item));
        }
        return itemPreviewDtos;
    }

    public ItemDetailDto getItemDetail(UUID itemId) {
        Item item = itemRepository.getReferenceById(itemId);
        return new ItemDetailDto(item);
    }

    @Transactional
    public ItemDetailDto createItem(ItemRequestDto itemRequestDto) {
        Item item = new Item(itemRequestDto);
        Quadrant quadrant = quadrantService.getQuadrant(itemRequestDto.quadrant());

        item.setCreationDate(LocalDate.now());
        item.setQuadrant(quadrant);

        itemRepository.save(item);
        return new ItemDetailDto(item);
    }

    @Transactional
    public ItemDetailDto updateItem(UUID itemId, ItemUpdateDto itemUpdateDto) {
        Item item = itemRepository.getReferenceById(itemId);

        if (itemUpdateDto.quadrant().isPresent()) {
            Quadrant quadrant = quadrantService.getQuadrant(itemUpdateDto.quadrant().get());
            item.setQuadrant(quadrant);
        }
        if (itemUpdateDto.flag() != null) item.setFlag(itemUpdateDto.flag());
        if (itemUpdateDto.authorEmail() != null) item.setAuthorEmail(itemUpdateDto.authorEmail());
        if (itemUpdateDto.title() != null) item.setTitle(itemUpdateDto.title());
        if (itemUpdateDto.ring() != null) item.setRing(itemUpdateDto.ring());
        if (itemUpdateDto.body() != null) item.setBody(itemUpdateDto.body());

        item.setUpdateDate(LocalDate.now());

        // add user to revision list
        return new ItemDetailDto(item);
    }

    @Transactional
    public ItemDetailDto publishItem(UUID itemId) throws Exception {
        Item item = itemRepository.getReferenceById(itemId);
        if (!item.isActive()) {
            item.setPublicationDate(LocalDate.now());
            item.setActive(true);
            return new ItemDetailDto(item);
        }
        throw new Exception("Item already published");
    }

    @Transactional
    public ItemDetailDto archiveItem(UUID itemId) throws Exception {
        Item item = itemRepository.getReferenceById(itemId);
        if (item.isActive()) {
            item.setPublicationDate(null);
            item.setActive(false);
            return new ItemDetailDto(item);
        }
        throw new Exception("Item already archived");
    }
}
