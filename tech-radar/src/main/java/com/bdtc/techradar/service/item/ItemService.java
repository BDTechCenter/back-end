package com.bdtc.techradar.service.item;

import com.bdtc.techradar.constant.Flag;
import com.bdtc.techradar.constant.Roles;
import com.bdtc.techradar.dto.item.*;
import com.bdtc.techradar.dto.user.UserDto;
import com.bdtc.techradar.infra.exception.validation.ItemAlreadyArchivedException;
import com.bdtc.techradar.infra.exception.validation.ItemAlreadyPublishedException;
import com.bdtc.techradar.model.Item;
import com.bdtc.techradar.model.Quadrant;
import com.bdtc.techradar.repository.ItemRepository;
import com.bdtc.techradar.service.auth.AuthHandler;
import com.bdtc.techradar.service.quadrant.QuadrantService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.jwt.Jwt;
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

    @Autowired
    private AuthHandler authHandler;


    public List<ItemPreviewDto> getItemsPreview() {
        List<ItemPreviewDto> itemPreviewDtos = new ArrayList<>();
        List<Item> items = itemRepository.findAllByIsActiveTrue();

        for (Item item : items) {
            itemPreviewDtos.add(new ItemPreviewDto(item));
        }
        return itemPreviewDtos;
    }

    public List<ItemAdminPreviewDto> getItemsAdminReview(Jwt tokenJwt) {
        List<ItemAdminPreviewDto> itemAdminPreviewDtos = new ArrayList<>();
        List<Item> items;

        UserDto authenticatedUser = new UserDto(tokenJwt);
        if (authenticatedUser.roles().contains(Roles.ADMIN)) {
            items = itemRepository.findAllByNeedAdminReviewTrue();
        } else {
            items = itemRepository.findAllByNeedAdminReviewTrueNotAdmin(authenticatedUser.networkUser());
        }
        for (Item item : items) {
            itemAdminPreviewDtos.add(new ItemAdminPreviewDto(item));
        }
        return itemAdminPreviewDtos;
    }

    public List<ItemMePreviewDto> getItemsMePreview(Jwt tokenJWT) {
        List<ItemMePreviewDto> itemMePreviewDtos = new ArrayList<>();
        List<Item> items;

        UserDto authenticatedUser = new UserDto(tokenJWT);
        // admin: return all
        if (authenticatedUser.roles().contains(Roles.ADMIN)) {
            items = itemRepository.findAll();
        } else { // user: return related to them
            items = itemRepository.findAllByAuthorEmail(authenticatedUser.networkUser());
        }

        for (Item item : items) itemMePreviewDtos.add(new ItemMePreviewDto(item));

        return itemMePreviewDtos;
    }

    public List<ItemPreviewDto> getAllItemsPreview() {
        List<ItemPreviewDto> itemPreviewDtos = new ArrayList<>();
        List<Item> items = itemRepository.findAll();

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
    public ItemDetailDto createItem(Jwt tokenJWT, ItemRequestDto itemRequestDto) {
        UserDto authenticatedUser = new UserDto(tokenJWT);
        authHandler.validateUserRole(authenticatedUser);

        Item item = new Item(itemRequestDto);
        Quadrant quadrant = quadrantService.getQuadrant(itemRequestDto.quadrant());

        if (authenticatedUser.roles().contains(Roles.ADMIN)) item.setNeedAdminReview(false);

        item.setCreationDate(LocalDate.now());
        item.setQuadrant(quadrant);
        item.setAuthor(authenticatedUser.username());
        item.setAuthorEmail(authenticatedUser.networkUser());
        item.setFlag(Flag.NEW);

        itemRepository.save(item);
        return new ItemDetailDto(item);
    }

    @Transactional
    public List<ItemDetailDto> createMultipleItems(Jwt tokenJWT, List<ItemRequestDto> itemRequestDtos) {
        List<ItemDetailDto> itemRequestDtosList = new ArrayList<>();
        for(ItemRequestDto itemRequestDto : itemRequestDtos) {
            itemRequestDtosList.add(createItem(tokenJWT, itemRequestDto));
        }
        return itemRequestDtosList;
    }

    @Transactional
    public ItemDetailDto updateItem(Jwt tokenJWT, UUID itemId, ItemUpdateDto itemUpdateDto) {
        UserDto authenticatedUser = new UserDto(tokenJWT);
        authHandler.validateUserRole(authenticatedUser);

        Item item = itemRepository.getReferenceById(itemId);

        if (authenticatedUser.roles().contains(Roles.ADMIN)) {
            item.setNeedAdminReview(false);
        } else {
            item.setNeedAdminReview(true);
        }

        if (itemUpdateDto.quadrant().isPresent()) {
            Quadrant quadrant = quadrantService.getQuadrant(itemUpdateDto.quadrant().get());
            item.setQuadrant(quadrant);
        }
        if (itemUpdateDto.title() != null) item.setTitle(itemUpdateDto.title());
        if (itemUpdateDto.ring() != null) item.setRing(itemUpdateDto.ring());
        if (itemUpdateDto.body() != null) item.setBody(itemUpdateDto.body());

        item.setUpdateDate(LocalDate.now());
        item.setRevisions(authenticatedUser.networkUser());
        item.setFlag(Flag.CHANGED);

        return new ItemDetailDto(item);
    }

    @Transactional
    public ItemDetailDto publishItem(Jwt tokenJWT, UUID itemId) {
        Item item = itemRepository.getReferenceById(itemId);
        // only admin can publish
        authHandler.validateUserIsAdmin(tokenJWT);
        item.setNeedAdminReview(false);

        if (!item.isActive()) {
            item.setPublicationDate(LocalDate.now());
            item.setActive(true);
            item.setFlag(Flag.NEW);
            return new ItemDetailDto(item);
        }
        throw new ItemAlreadyPublishedException();
    }

    @Transactional
    public ItemDetailDto archiveItem(Jwt tokenJWT, UUID itemId) {
        Item item = itemRepository.getReferenceById(itemId);
        // only author or admin can archive
        authHandler.validateAuthorOrAdmin(tokenJWT, item);

        if (item.isActive()) {
            item.setPublicationDate(null);
            item.setActive(false);
            return new ItemDetailDto(item);
        }
        throw new ItemAlreadyArchivedException();
    }

    @Transactional
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void updateItemFlag() {
        List<Item> items = itemRepository.findAll();
        LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);

        for (Item item : items) {
            if (item.getUpdateDate() != null && item.getCreationDate().isBefore(oneWeekAgo) && item.getUpdateDate().isBefore(oneWeekAgo)) {
                item.setFlag(Flag.DEFAULT);
            }
        }
    }
}
