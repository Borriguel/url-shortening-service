package dev.borriguel.url_shortening_service.exception;

import dev.borriguel.url_shortening_service.dto.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse notFoundExceptionHandler(Exception ex, HttpServletRequest http) {
        return new ApiErrorResponse(ex.getMessage(), http.getRequestURI(), HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), LocalDateTime.now());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex, HttpServletRequest http) {
        return new ApiErrorResponse(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage(), http.getRequestURI(), HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
    }

    @ExceptionHandler(GenShortUrlException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse genShortUrlExceptionHandler(Exception ex, HttpServletRequest http) {
        return new ApiErrorResponse(ex.getMessage(), http.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());
    }

}
