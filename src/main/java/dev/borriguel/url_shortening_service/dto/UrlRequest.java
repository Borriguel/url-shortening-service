package dev.borriguel.url_shortening_service.dto;

import org.hibernate.validator.constraints.URL;

public record UrlRequest(@URL(message = "Invalid URL format") String url) {
}
