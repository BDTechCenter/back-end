package com.bdtc.technews.service.news.backup;

import com.bdtc.technews.model.News;
import com.bdtc.technews.model.NewsBackup;
import com.bdtc.technews.repository.NewsBackupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsBackupService {

    @Autowired
    private NewsBackupRepository newsBackupRepository;

    public void createNewsBackup(News news) {
        newsBackupRepository.save(new NewsBackup(news));
    }
}
