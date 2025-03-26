package dev.borriguel.url_shortening_service.dto;

import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

public record ApiErrorResponse(String error, String uri, HttpStatusCode status, int code, LocalDateTime timestamp) {
}
