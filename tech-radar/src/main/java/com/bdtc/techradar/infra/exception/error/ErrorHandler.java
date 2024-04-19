package com.bdtc.techradar.infra.exception.error;


import com.bdtc.techradar.infra.exception.validation.ItemAlreadyArchivedException;
import com.bdtc.techradar.infra.exception.validation.ItemAlreadyPublishedException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
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
                .body(errors.stream().map(ValidationErrorData::new).toList());
    }

    @ExceptionHandler(ItemAlreadyPublishedException.class)
    public ResponseEntity itemAlreadyPublishedHandler(ItemAlreadyPublishedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ValidationErrorData("item", e.getMessage()));
    }

    @ExceptionHandler(ItemAlreadyArchivedException.class)
    public ResponseEntity itemAlreadyArchivedHandler(ItemAlreadyArchivedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ValidationErrorData("item", e.getMessage()));
    }
}
