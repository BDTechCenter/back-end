package com.bdtc.technews.service.news;

import com.bdtc.technews.dto.*;
import com.bdtc.technews.http.auth.service.AuthClientService;
import com.bdtc.technews.infra.exception.validation.PermissionException;
import com.bdtc.technews.model.News;
import com.bdtc.technews.model.NewsUpVoter;
import com.bdtc.technews.model.Tag;
import com.bdtc.technews.repository.NewsRepository;
import com.bdtc.technews.repository.NewsUpVoterRepository;
import com.bdtc.technews.service.news.backup.NewsBackupService;
import com.bdtc.technews.service.news.utils.*;
import com.bdtc.technews.service.tag.TagService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private AuthClientService authService;

    @Autowired
    private RoleAuthHandler roleAuthHandler;

    @Transactional
    public NewsDetailingDto createNews(String tokenJWT, NewsRequestDto newsDto) {
        UserDto authenticatedUser = authService.getUser(tokenJWT);
        roleAuthHandler.validateUserRole(authenticatedUser);

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
                dateHandler.formatDate(news.getUpdateDate())
        );
    }

    public Page<NewsPreviewDto> getNewsPreview(Pageable pageable, FilterOption filter, String titleFilter, String tags) {
        Page<News> newsPage;

        List<FilterOption> filterOptions = List.of(FilterOption.view, FilterOption.latest, FilterOption.relevance);
        filterHandler.validateFilter(filterOptions, filter);

        if(StringUtils.isNotBlank(tags)) {
            List<String> tagList = Arrays.asList(tags.split(","));
            newsPage = newsRepository.findByTagNames(pageable, tagList, (long) tagList.size());
        } else if(StringUtils.isBlank(titleFilter)) {
            switch (filter) {
                case view:
                    newsPage = newsRepository.findByIsPublishedTrueOrderByViewsDesc(pageable);
                    break;
                case latest:
                    newsPage = newsRepository.findByIsPublishedTrueAndLatestUpdate(pageable);
                    break;
                case relevance:
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
    public NewsDetailingWUpVoteDto getNewsById(String tokenJWT, UUID newsId) {
        News news = newsRepository.getReferenceById(newsId);
        news.addAView();

        String currentUserEmail = authService.getUser(tokenJWT).networkUser();
        boolean alreadyUpVoted = newsUpVoterRepository.existsByVoterEmailAndNewsId(currentUserEmail, news.getId());

        return new NewsDetailingWUpVoteDto(
                news,
                tagHandler.convertSetTagToSetString(news.getTags()),
                dateHandler.formatDate(news.getUpdateDate()),
                alreadyUpVoted
        );
    }

    @Transactional
    public NewsDetailingDto publishNews(String tokenJWT, UUID newsId) {
        News news = newsRepository.getReferenceById(newsId);

        String currentUserEmail = authService.getUser(tokenJWT).networkUser();
        if(!currentUserEmail.equals(news.getAuthorEmail())) throw new PermissionException();

        news.publishNews();
        news.setPublicationDate(dateHandler.getCurrentDateTime());
        return new NewsDetailingDto(
                news,
                tagHandler.convertSetTagToSetString(news.getTags()),
                dateHandler.formatDate(news.getUpdateDate())
        );
    }

    @Transactional
    public NewsDetailingDto archiveNews(String tokenJWT, UUID newsId) {
        News news = newsRepository.getReferenceById(newsId);

        String currentUserEmail = authService.getUser(tokenJWT).networkUser();
        if(!currentUserEmail.equals(news.getAuthorEmail())) throw new PermissionException();

        news.archiveNews();
        return new NewsDetailingDto(
                news,
                tagHandler.convertSetTagToSetString(news.getTags()),
                dateHandler.formatDate(news.getUpdateDate())
        );
    }


    public Page<NewsPreviewDto> getNewsByAuthor(String tokenJWT, Pageable pageable, FilterOption filter) {
        String currentUserEmail = authService.getUser(tokenJWT).networkUser();
        Page<News> newsPage;

        List<FilterOption> filterOptions = List.of(FilterOption.published, FilterOption.archived, FilterOption.empty);
        filterHandler.validateFilter(filterOptions, filter);

        if(!filter.equals(FilterOption.empty)) {
            boolean isPublished = false;
            switch(filter) {
                case published:
                    isPublished = true;
                    break;
                case archived:
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
    public NewsDetailingDto updateNews(String tokenJWT, UUID newsId, NewsUpdateDto updateDto) {
        News news = newsRepository.getReferenceById(newsId);
        newsBackupService.createNewsBackup(news, null);

        String currentUserEmail = authService.getUser(tokenJWT).networkUser();
        if(!currentUserEmail.equals(news.getAuthorEmail())) throw new PermissionException();

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
                dateHandler.formatDate(news.getUpdateDate())
        );
    }

    public News getNews(UUID id) {
        if(!newsRepository.existsById(id)) throw new EntityNotFoundException();
        News news = newsRepository.getReferenceById(id);
        return news;
    }

    @Transactional
    public void addUpVoteToNews(String tokenJWT, UUID newsId) {
        if(!newsRepository.existsById(newsId)) throw new EntityNotFoundException();

        News news = newsRepository.getReferenceById(newsId);
        String currentUserEmail = authService.getUser(tokenJWT).networkUser();

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
