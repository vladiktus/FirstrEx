package com.tus.springcourse.firstrex.exception;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.tus.springcourse.firstrex.model.ErrorResponse;

/**
 * A global exception handler for handling exceptions thrown by controllers within the application.
 * This class extends ResponseEntityExceptionHandler and provides exception handling mechanisms
 * for various types of exceptions.
 */
@RestControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles generic exceptions.
     *
     * @param exception The exception to handle.
     * @return ResponseEntity with status 500 (Internal Server Error) and an ErrorResponse body.
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleException(Exception exception) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().code(1).massage("Unexpected exception").errTime(LocalDate.now()).build());
    }


    /**
     * Handles EntityAlreadyExist exceptions.
     *
     * @param exception The EntityAlreadyExist exception to handle.
     * @return ResponseEntity with status 500 (Internal Server Error) and an ErrorResponse body.
     */
    @ExceptionHandler(EntityAlreadyExist.class)
    public final ResponseEntity<Object> handleEntityAlreadyExistException(EntityAlreadyExist exception) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().code(1).massage("Entity already exist").errTime(LocalDate.now()).build());
    }

    /**
     * Handles EntityNotFound exceptions.
     *
     * @param exception The EntityNotFound exception to handle.
     * @return ResponseEntity with status 404 (Not Found) and an ErrorResponse body.
     */
    @ExceptionHandler
    public final ResponseEntity<Object> handleEntityNotFoundException(EntityNotFound exception) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().code(2).massage("Entity not found").errTime(LocalDate.now()).build());
    }

    /**
     * Handles SqlErrorException exceptions.
     *
     * @param exception The SqlErrorException exception to handle.
     * @return ResponseEntity with status 500 (Internal Server Error) and an ErrorResponse body.
     */
    @ExceptionHandler
    public final ResponseEntity<Object> handleSqlErrorException(SqlErrorException exception) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().code(3).massage("Sql err").errTime(LocalDate.now()).build());
    }


}
