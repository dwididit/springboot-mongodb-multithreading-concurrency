package dev.dwidi.springbootmongodbmultithreadingconcurrency.exceptions;

import dev.dwidi.springbootmongodbmultithreadingconcurrency.dto.PublicResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<PublicResponseDTO<String>> handleGlobalException(WebRequest request, Exception exception) {
        log.error("Exception occurred: {}, request: {}", exception, request);
        return ResponseEntity.ok(new PublicResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), null));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<PublicResponseDTO<String>> handleUserNotFoundException(WebRequest request, UserNotFoundException exception) {
        log.error("UserNotFoundException occurred: {}, request: {}", exception, request);
        return ResponseEntity.ok(new PublicResponseDTO<>(HttpStatus.NOT_FOUND.value(), exception.getMessage(), null));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<PublicResponseDTO<String>> handleUserAlreadyExistsException(WebRequest request, UserAlreadyExistsException exception) {
        log.error("UserAlreadyExistException occurred: {}, request: {}", exception, request);
        return ResponseEntity.ok(new PublicResponseDTO<>(HttpStatus.CONFLICT.value(), exception.getMessage(), null));
    }
}