package com.bdtc.technews.infra.exception.validation;

public class ExceededTheExistingBackupLevelException extends RuntimeException{
    public ExceededTheExistingBackupLevelException(int backupListSize) {
        super(String.format("Exceeded the existing backup level (%s)!", backupListSize));
    }
}
