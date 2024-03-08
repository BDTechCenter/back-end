package com.bdtc.technews.infra.exception.error;

import com.bdtc.technews.infra.exception.validation.BusinessRuleException;
import com.bdtc.technews.infra.exception.validation.ExceededTheExistingBackupLevelException;
import com.bdtc.technews.infra.exception.validation.ThereIsNoBackupForThisNewsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity error404Handler() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleError400(MethodArgumentNotValidException exception) {
        var errors = exception.getFieldErrors();
        return ResponseEntity.badRequest()
                .body(errors.stream()
                        .map(ValidationErrorData::new).toList());
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity businessRuleExceptionHandler(BusinessRuleException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(ThereIsNoBackupForThisNewsException.class)
    public ResponseEntity noBackupForNewsHandler(ThereIsNoBackupForThisNewsException exception) {
        return ResponseEntity.badRequest().body(new ValidationErrorData("newsId", exception.getMessage()));
    }

    @ExceptionHandler(ExceededTheExistingBackupLevelException.class)
    public ResponseEntity noBackupLevelForNewsHandler(ExceededTheExistingBackupLevelException exception) {
        return ResponseEntity.badRequest().body(new ValidationErrorData("level", exception.getMessage()));
    }
}
