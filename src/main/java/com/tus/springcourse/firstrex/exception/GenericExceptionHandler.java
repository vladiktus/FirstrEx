package com.tus.springcourse.firstrex.exception;

import com.tus.springcourse.firstrex.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.Timestamp;
import java.time.LocalDate;

@RestControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleException(Exception exception) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().code(1).massage("Unexpected exception").errTime(LocalDate.now()).build());
    }

    @ExceptionHandler(EntityAlreadyExist.class)
    public final ResponseEntity<Object> handleEntityAlreadyExistException(EntityAlreadyExist exception) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().code(1).massage("Entity already exist").errTime(LocalDate.now()).build());
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleEntityNotFoundException(EntityNotFound exception){
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().code(2).massage("Entity not found").errTime(LocalDate.now()).build());
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleSqlErrorException(SqlErrorException exception){
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().code(3).massage("Sql err").errTime(LocalDate.now()).build());
    }


}
