package com.bdtc.technews.service.news.backup;

import com.bdtc.technews.dto.news.NewsBackupDto;
import com.bdtc.technews.dto.news.NewsDetailingDto;
import com.bdtc.technews.model.News;
import com.bdtc.technews.model.NewsBackup;
import com.bdtc.technews.repository.NewsBackupRepository;
import com.bdtc.technews.repository.NewsRepository;
import com.bdtc.technews.service.news.backup.utils.BackupLevelHandler;
import com.bdtc.technews.service.news.utils.DateHandler;
import com.bdtc.technews.service.news.utils.TagHandler;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NewsBackupService {

    @Autowired
    private NewsBackupRepository newsBackupRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private BackupLevelHandler backupLevelHandler;

    @Autowired
    private TagHandler tagHandler;

    @Autowired
    private DateHandler dateHandler;

    @Transactional
    public void createNewsBackup(News news, Long backupId) {
        boolean exceededBackupLimit = newsBackupRepository.hasMoreThanBackupLimit(news.getId(), 2);
        if(exceededBackupLimit) {
            List<NewsBackup> backupList = newsBackupRepository.findAllByNewsId(news.getId());
            if(backupId == null) {
                newsBackupRepository.delete(backupList.get(0));
            } else {
                newsBackupRepository.deleteById(backupId);
            }
        }

        newsBackupRepository.save(new NewsBackup(news));
    }

    public NewsBackupDto getNewsBackup(UUID newsId, int backupLevel) {
        List<NewsBackup> backupList = newsBackupRepository.findAllByNewsId(newsId);
        backupLevelHandler.verifyIdBackupLevelExist(backupList.size(), backupLevel, newsId  );
        NewsBackup news;

        switch (backupLevel) {
            case 1:
                news = backupList.get(2);
                break;
            case 2:
                news = backupList.get(1);
                break;
            default:
                news = backupList.get(0);
        }
        return new NewsBackupDto(news);
    }

    @Transactional
    public NewsDetailingDto restoreNewsFromABackup(UUID newsId, Long backupId) {
        News news = newsRepository.getReferenceById(newsId);
        NewsBackup newsBackup = newsBackupRepository.getReferenceById(backupId);
        createNewsBackup(news, backupId);

        news.restoreBackup(newsBackup);
        return new NewsDetailingDto(
                news,
                tagHandler.convertSetTagToSetString(news.getTags()),
                dateHandler.formatDate(news.getCreationDate()),
                dateHandler.formatDate(news.getUpdateDate())
        );
    }
}
