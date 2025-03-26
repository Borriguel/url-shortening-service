package dev.borriguel.url_shortening_service.dto;

import java.time.LocalDateTime;

public record UrlStatResponse(Long id, String url, String shortUrl, LocalDateTime createdAt, LocalDateTime updatedAt, int accessCount) {
}
