package com.bdtc.techradar.infra.exception.error.util;

import com.bdtc.techradar.infra.exception.error.ValidationErrorData;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class UniqueConstraintHandler {
    public boolean isUniqueConstraintException(SQLException sqlException) {
        String uniqueConstraintMessage = "duplicate key value violates unique constraint";
        return sqlException.getMessage().contains(uniqueConstraintMessage);
    }

    public ResponseEntity getUniqueConstraintResponse(SQLException sqlException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ValidationErrorData("FIELDS HERE", sqlException.getMessage()));
    }

}
