package com.bdtc.technews.service.news;

import com.bdtc.technews.contants.FilterOption;
import com.bdtc.technews.dto.news.*;
import com.bdtc.technews.dto.user.UserDto;
import com.bdtc.technews.infra.exception.validation.PermissionException;
import com.bdtc.technews.model.News;
import com.bdtc.technews.model.NewsUpVoter;
import com.bdtc.technews.model.Tag;
import com.bdtc.technews.repository.NewsRepository;
import com.bdtc.technews.repository.NewsUpVoterRepository;
import com.bdtc.technews.service.auth.AuthorizationHandler;
import com.bdtc.technews.service.news.backup.NewsBackupService;
import com.bdtc.technews.service.news.utils.DateHandler;
import com.bdtc.technews.service.news.utils.FilterHandler;
import com.bdtc.technews.service.news.utils.ImageHandler;
import com.bdtc.technews.service.news.utils.TagHandler;
import com.bdtc.technews.service.tag.TagService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsUpVoterRepository newsUpVoterRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private DateHandler dateHandler;

    @Autowired
    private TagHandler tagHandler;

    @Autowired
    private ImageHandler imageHandler;

    @Autowired
    private NewsBackupService newsBackupService;

    @Autowired
    private FilterHandler filterHandler;

    @Autowired
    private AuthorizationHandler authorizationHandler;

    @Transactional
    public NewsDetailingDto createNews(Jwt tokenJWT, NewsRequestDto newsDto) {
        UserDto authenticatedUser = new UserDto(tokenJWT);
        authorizationHandler.validateUserRole(authenticatedUser);

        News news = new News(newsDto);
        LocalDateTime dateNow = dateHandler.getCurrentDateTime();
        Set<Tag> tagSet = tagService.getTagSet(newsDto.tags());
        String imageUrl = imageHandler.saveImageToUploadDir(newsDto.image());

        news.setAuthorEmail(authenticatedUser.networkUser());
        news.setAuthor(authenticatedUser.username());
        news.setCreationDate(dateNow);
        news.setUpdateDate(dateNow);
        news.setTags(tagSet);
        news.setImageUrl(imageUrl);
        if(news.isPublished()) news.setPublicationDate(dateNow);

        newsRepository.save(news);
        return new NewsDetailingDto(
                news,
                tagHandler.convertSetTagToSetString(news.getTags()),
                dateHandler.formatDate(news.getCreationDate()),
                dateHandler.formatDate(news.getUpdateDate())
        );
    }

    public Page<NewsPreviewDto> getNewsPreview(Pageable pageable, String filter, String titleFilter, String tags) {
        Page<News> newsPage;

        List<FilterOption> filterOptions = List.of(FilterOption.VIEW, FilterOption.LATEST, FilterOption.RELEVANCE);
        FilterOption filterOption = FilterOption.stringToFilterOption(filter);
        filterHandler.validateFilter(filterOptions, filterOption);

        if(StringUtils.isNotBlank(tags)) {
            List<String> tagList = Arrays.asList(tags.split(","));
            newsPage = newsRepository.findByTagNames(pageable, tagList, (long) tagList.size());
        } else if(StringUtils.isBlank(titleFilter)) {
            switch (filterOption) {
                case VIEW:
                    newsPage = newsRepository.findByIsPublishedTrueOrderByViewsDesc(pageable);
                    break;
                case LATEST:
                    newsPage = newsRepository.findByIsPublishedTrueAndLatestUpdate(pageable);
                    break;
                case RELEVANCE:
                    newsPage = newsRepository.getNewsByRelevance(pageable);
                    break;
                default:
                    newsPage = newsRepository.findAllByIsPublishedTrue(pageable);
                    break;
            }
        } else {
            newsPage = newsRepository.findAllByLikeTitleFilter(pageable, titleFilter);
        }
        return newsPage.map(news -> new NewsPreviewDto(
                        news,
                        dateHandler.formatDate(news.getUpdateDate())
                )
        );
    }

    @Transactional
    public NewsDetailingWUpVoteDto getNewsById(Jwt tokenJWT, UUID newsId) {
        UserDto userDto = new UserDto(tokenJWT);
        News news = newsRepository.getReferenceById(newsId);

        if(!news.isPublished() &&
                !authorizationHandler.userHasAuthorization(userDto, news.getAuthorEmail())
        ) throw new PermissionException();

        news.addAView();
        boolean alreadyUpVoted = newsUpVoterRepository.existsByVoterEmailAndNewsId(
                userDto.networkUser(), news.getId()
        );

        return new NewsDetailingWUpVoteDto(
                news,
                tagHandler.convertSetTagToSetString(news.getTags()),
                dateHandler.formatDate(news.getUpdateDate()),
                alreadyUpVoted
        );
    }

    @Transactional
    public NewsDetailingDto publishNews(Jwt tokenJWT, UUID newsId) {
        News news = newsRepository.getReferenceById(newsId);

        UserDto userDto = new UserDto(tokenJWT);
        authorizationHandler.userHasAuthorization(userDto, news.getAuthorEmail());

        news.publishNews();
        news.setPublicationDate(dateHandler.getCurrentDateTime());
        return new NewsDetailingDto(
                news,
                tagHandler.convertSetTagToSetString(news.getTags()),
                dateHandler.formatDate(news.getCreationDate()),
                dateHandler.formatDate(news.getUpdateDate())
        );
    }

    @Transactional
    public NewsDetailingDto archiveNews(Jwt tokenJWT, UUID newsId) {
        News news = newsRepository.getReferenceById(newsId);

        UserDto userDto = new UserDto(tokenJWT);
        authorizationHandler.userHasAuthorization(userDto, news.getAuthorEmail());

        news.archiveNews();
        return new NewsDetailingDto(
                news,
                tagHandler.convertSetTagToSetString(news.getTags()),
                dateHandler.formatDate(news.getCreationDate()),
                dateHandler.formatDate(news.getUpdateDate())
        );
    }


    public Page<NewsPreviewDto> getNewsByAuthor(Jwt tokenJWT, Pageable pageable, String filter) {
        String currentUserEmail = new UserDto(tokenJWT).networkUser();
        Page<News> newsPage;

        List<FilterOption> filterOptions = List.of(FilterOption.PUBLISHED, FilterOption.ARCHIVED, FilterOption.EMPTY);
        FilterOption filterOption = FilterOption.stringToFilterOption(filter);
        filterHandler.validateFilter(filterOptions, filterOption);

        if(!filterOption.equals(FilterOption.EMPTY)) {
            boolean isPublished = false;
            switch(filterOption) {
                case PUBLISHED:
                    isPublished = true;
                    break;
                case ARCHIVED:
                    break;
            }
            newsPage = newsRepository.getNewsByAuthorAndPublication(currentUserEmail, pageable, isPublished);
        } else {
            newsPage = newsRepository.getNewsByAuthor(currentUserEmail, pageable);
        }

        return newsPage.map(news -> new NewsPreviewDto(
                        news,
                        dateHandler.formatDate(news.getUpdateDate())
                )
        );
    }


    @Transactional
    public NewsDetailingDto updateNews(Jwt tokenJWT, UUID newsId, NewsUpdateDto updateDto) {
        News news = newsRepository.getReferenceById(newsId);
        newsBackupService.createNewsBackup(news, null);

        UserDto userDto = new UserDto(tokenJWT);
        authorizationHandler.userHasAuthorization(userDto, news.getAuthorEmail());

        if(updateDto.title() !=null) news.updateTitle(updateDto.title());
        if(updateDto.body() !=null) news.updateBody(updateDto.body());

        if(updateDto.tags() !=null) {
            Set<Tag> tagSet = tagService.getTagSet(updateDto.tags());
            news.setTags(tagSet);
        }

        if(updateDto.image() !=null) {
            String imageUrl = imageHandler.saveImageToUploadDir(updateDto.image());
            news.setImageUrl(imageUrl);
        }

        news.setUpdateDate(dateHandler.getCurrentDateTime());

        return new NewsDetailingDto(
                news,
                tagHandler.convertSetTagToSetString(news.getTags()),
                dateHandler.formatDate(news.getCreationDate()),
                dateHandler.formatDate(news.getUpdateDate())
        );
    }

    public News getNews(UUID id) {
        if(!newsRepository.existsById(id)) throw new EntityNotFoundException();
        News news = newsRepository.getReferenceById(id);
        return news;
    }

    @Transactional
    public void addUpVoteToNews(Jwt tokenJWT, UUID newsId) {
        if(!newsRepository.existsById(newsId)) throw new EntityNotFoundException();

        News news = newsRepository.getReferenceById(newsId);
        String currentUserEmail = new UserDto(tokenJWT).networkUser();

        if(newsUpVoterRepository.existsByVoterEmailAndNewsId(currentUserEmail, news.getId())) {
            newsUpVoterRepository.deleteByVoterEmailAndNewsId(currentUserEmail, newsId);
            news.removeUpVote();
        }else {
            NewsUpVoter newsUpVoter = new NewsUpVoter(currentUserEmail, news);
            news.getNewsUpVoters().add(newsUpVoter);
            newsUpVoterRepository.save(newsUpVoter);
            news.addUpVote();
        }
    }
}
