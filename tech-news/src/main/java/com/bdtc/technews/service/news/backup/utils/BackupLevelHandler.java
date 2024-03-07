package com.bdtc.technews.service.news.backup.utils;

import com.bdtc.technews.infra.exception.validation.ExceededTheExistingBackupLevelException;
import com.bdtc.technews.infra.exception.validation.ThereIsNoBackupForThisNewsException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BackupLevelHandler {

    public void verifyIdBackupLevelExist(int backupListSize, int backupLevelRequested, UUID newsId) {
        if(backupListSize == 0) throw new ThereIsNoBackupForThisNewsException(newsId);
        if(backupLevelRequested > backupListSize) throw new ExceededTheExistingBackupLevelException(backupListSize);
    }
}
