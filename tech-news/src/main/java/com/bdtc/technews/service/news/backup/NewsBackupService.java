package com.bdtc.technews.service.news.backup;

import com.bdtc.technews.dto.NewsBackupDto;
import com.bdtc.technews.model.News;
import com.bdtc.technews.model.NewsBackup;
import com.bdtc.technews.repository.NewsBackupRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NewsBackupService {

    @Autowired
    private NewsBackupRepository newsBackupRepository;

    @Transactional
    public void createNewsBackup(News news) {
        var exceededBackupLimit = newsBackupRepository.hasMoreThanBackupLimit(news.getId(), 2);
        if(exceededBackupLimit) {
            var backupList = newsBackupRepository.findAllByNewsId(news.getId());
            newsBackupRepository.delete(backupList.get(0));
        }

        newsBackupRepository.save(new NewsBackup(news));
    }

    public NewsBackupDto getNewsBackup(UUID newsId, int backupLevel) {
        List<NewsBackup> backupList = newsBackupRepository.findAllByNewsId(newsId);
        NewsBackup news;

        switch (backupLevel) {
            case 1:
                news = backupList.get(2);
                break;
            case 2:
                news = backupList.get(1);
                break;
            case 3:
                news = backupList.get(0);
                break;
            default:
                news = backupList.get(0);
        }
        return new NewsBackupDto(news);
    }
}
