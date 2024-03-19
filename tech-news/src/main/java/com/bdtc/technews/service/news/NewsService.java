package com.bdtc.technews.service.news;

import com.bdtc.technews.dto.NewsDetailingDto;
import com.bdtc.technews.dto.NewsPreviewDto;
import com.bdtc.technews.dto.NewsRequestDto;
import com.bdtc.technews.dto.NewsUpdateDto;
import com.bdtc.technews.infra.exception.validation.ConflictInPathParameters;
import com.bdtc.technews.model.News;
import com.bdtc.technews.model.Tag;
import com.bdtc.technews.repository.NewsRepository;
import com.bdtc.technews.service.news.backup.NewsBackupService;
import com.bdtc.technews.service.news.utils.DateHandler;
import com.bdtc.technews.service.news.utils.ImageHandler;
import com.bdtc.technews.service.news.utils.TagHandler;
import com.bdtc.technews.service.tag.TagService;
import jakarta.transaction.Transactional;
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
    private TagService tagService;

    @Autowired
    private DateHandler dateHandler;

    @Autowired
    private TagHandler tagHandler;

    @Autowired
    private ImageHandler imageHandler;

    @Autowired
    private NewsBackupService newsBackupService;

    @Transactional
    public NewsDetailingDto createNews(NewsRequestDto newsDto) {
        News news = new News(newsDto);
        LocalDateTime dateNow = dateHandler.getCurrentDateTime();
        Set<Tag> tagSet = tagService.getTagSet(newsDto.tags());
        String imageUrl = imageHandler.saveImageToUploadDir(newsDto.image());

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

    public Page<NewsPreviewDto> getNewsPreview(Pageable pageable, boolean sortByView, boolean latest) {
        Page<News> newsPage;

        if(sortByView && latest) throw new ConflictInPathParameters("We can't sort by viewed and most recent at the same time. Choose just one of the parameters 'sortByView' or 'latest'");

        if(sortByView) {
            newsPage = newsRepository.findByIsPublishedTrueOrderByViewsDesc(pageable);
        } else if (latest) {
            newsPage = newsRepository.findByIsPublishedTrueAndLatestUpdate(pageable);
        } else {
            newsPage = newsRepository.findAllByIsPublishedTrue(pageable);
        }
        return newsPage.map(news -> new NewsPreviewDto(
                news,
                dateHandler.formatDate(news.getUpdateDate())
                )
        );
    }

    @Transactional
    public NewsDetailingDto getNewsById(UUID newsId) {
        News news = newsRepository.getReferenceById(newsId);
        news.addAView();
        return new NewsDetailingDto(
                news,
                tagHandler.convertSetTagToSetString(news.getTags()),
                dateHandler.formatDate(news.getUpdateDate())
        );
    }

    public Page<NewsPreviewDto> getNewsPreviewFilteringByTags(Pageable pageable, String tags) {
        List<String> tagList = Arrays.asList(tags.split(","));
        Page<News> newsPage = newsRepository.findByTagNames(pageable, tagList, (long) tagList.size());
        return newsPage.map(news -> new NewsPreviewDto(
                        news,
                        dateHandler.formatDate(news.getUpdateDate())
                )
        );
    }

    @Transactional
    public NewsDetailingDto publishNews(UUID newsId) {
        News news = newsRepository.getReferenceById(newsId);
        news.publishNews();
        news.setPublicationDate(dateHandler.getCurrentDateTime());
        return new NewsDetailingDto(
                news,
                tagHandler.convertSetTagToSetString(news.getTags()),
                dateHandler.formatDate(news.getUpdateDate())
        );
    }

    @Transactional
    public NewsDetailingDto archiveNews(UUID newsId) {
        News news = newsRepository.getReferenceById(newsId);
        news.archiveNews();
        return new NewsDetailingDto(
                news,
                tagHandler.convertSetTagToSetString(news.getTags()),
                dateHandler.formatDate(news.getUpdateDate())
        );
    }

    public Page<NewsPreviewDto> getArchivedNewsPreview(Pageable pageable) {
        Page<News> newsPage = newsRepository.findAllByIsPublishedFalse(pageable);
        return newsPage.map(news -> new NewsPreviewDto(
                        news,
                        dateHandler.formatDate(news.getUpdateDate())
                )
        );
    }


    @Transactional
    public NewsDetailingDto updateNews(UUID newsId, NewsUpdateDto updateDto) {
        News news = newsRepository.getReferenceById(newsId);
        newsBackupService.createNewsBackup(news, null);

        if(updateDto.title() !=null) news.updateTitle(updateDto.title());
        if(updateDto.summary() !=null) news.updateSummary(updateDto.summary());
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
        News news = newsRepository.getReferenceById(id);
        return news;
    }
}
