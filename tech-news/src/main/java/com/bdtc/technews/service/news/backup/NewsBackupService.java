package com.bdtc.technews.service.news.backup;

import com.bdtc.technews.model.News;
import com.bdtc.technews.model.NewsBackup;
import com.bdtc.technews.repository.NewsBackupRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
