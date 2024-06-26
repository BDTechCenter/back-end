package com.bdtc.techradar.infra.exception.error;


import com.bdtc.techradar.infra.exception.validation.*;
import com.bdtc.techradar.infra.exception.error.util.UniqueConstraintHandler;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
public class ErrorHandler {
    @Autowired
    private UniqueConstraintHandler uniqueConstraintHandler;

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
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ValidationErrorData("item", e.getMessage()));
    }

    @ExceptionHandler(ItemAlreadyArchivedException.class)
    public ResponseEntity itemAlreadyArchivedHandler(ItemAlreadyArchivedException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ValidationErrorData("item", e.getMessage()));
    }

    @ExceptionHandler(PermissionException.class)
    public ResponseEntity permissionDeniedHandler(PermissionException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ValidationErrorData("authorization", e.getMessage()));
    }

    @ExceptionHandler(AuthClientInvalidTokenException.class)
    public ResponseEntity invalidTokenHandler(AuthClientInvalidTokenException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ValidationErrorData("token", e.getMessage()));
    }

    @ExceptionHandler(UnauthorizedByRolesException.class)
    public ResponseEntity UnauthorizedByCurrentRolesHandler(UnauthorizedByRolesException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ValidationErrorData("roles", e.getMessage()));
    }

    @ExceptionHandler(QuadrantAlreadyExistsException.class)
    public ResponseEntity QuadrantAlreadyExistsException(UnauthorizedByRolesException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ValidationErrorData("quandrant", e.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity UniqueConstraintViolationHandler(DataIntegrityViolationException ex) {
        Throwable rootCause = ex.getRootCause();

        if (rootCause instanceof SQLException sqlException && uniqueConstraintHandler.isUniqueConstraintException(sqlException)) {
            return uniqueConstraintHandler.getUniqueConstraintResponse(sqlException);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ValidationErrorData("", "Unknown constraint violation occurred: " + ex.getMessage()));
        }
    }
}