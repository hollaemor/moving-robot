package org.hollaemor.movingrobot.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hollaemor.movingrobot.exception.InvalidMoveException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationControllerAdvice {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorMessage> handleValidationException(MethodArgumentNotValidException e) {
        var validationMessage = e.getBindingResult().getAllErrors()
                .stream().map(error -> error.getDefaultMessage()).findFirst().orElse(e.getMessage());

        return badRequest(validationMessage);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<ErrorMessage> handleNotReadableException() {
        return badRequest("Invalid request");
    }

    @ExceptionHandler(InvalidMoveException.class)
    ResponseEntity<ErrorMessage> handleInvalidMove(InvalidMoveException e) {
        return badRequest(e.getMessage());
    }

    @ExceptionHandler(AssertionError.class)
    ResponseEntity<ErrorMessage> handleAssertionErrors(AssertionError e) {
        return badRequest(e.getMessage());
    }

    private ResponseEntity<ErrorMessage> badRequest(String message) {
        return ResponseEntity.badRequest().body(new ErrorMessage(message));
    }

    @Data
    @AllArgsConstructor
    private class ErrorMessage {
        private String message;
    }
}
