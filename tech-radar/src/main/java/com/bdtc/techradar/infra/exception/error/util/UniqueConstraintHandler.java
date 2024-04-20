package com.bdtc.techradar.infra.exception.error.util;

import com.bdtc.techradar.infra.exception.error.ValidationErrorData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UniqueConstraintHandler {
    public boolean isUniqueConstraintException(SQLException sqlException) {
        String uniqueConstraintMessage = "duplicate key value violates unique constraint";
        return sqlException.getMessage().contains(uniqueConstraintMessage);
    }

    public ResponseEntity getUniqueConstraintResponse(SQLException sqlException) {
        String errorMessage = sqlException.getMessage();
        String fieldKey = "";
        String fieldValue = "";

        Matcher matcher = getFieldKeyAndValue(errorMessage);
        if (matcher.find()) {
            fieldKey = matcher.group(1);
            fieldValue = matcher.group(2);
        }
        String personalizedMessage = "ERROR: Duplicate key value violates unique constraint for: " + fieldValue;
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ValidationErrorData(fieldKey, personalizedMessage));
    }

    private Matcher getFieldKeyAndValue(String errorMessage) {
        Pattern pattern = Pattern.compile("Key \\((.*?)\\)=\\((.*?)\\)");
        return pattern.matcher(errorMessage);
    }
}
