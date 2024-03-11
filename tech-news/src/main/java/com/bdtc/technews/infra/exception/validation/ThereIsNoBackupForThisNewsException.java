package com.bdtc.technews.infra.exception.validation;

import java.util.UUID;

public class ThereIsNoBackupForThisNewsException extends RuntimeException{
    public ThereIsNoBackupForThisNewsException(UUID newsId) {
        super(String.format("There is no backup for news with id %s", newsId));
    }
}
