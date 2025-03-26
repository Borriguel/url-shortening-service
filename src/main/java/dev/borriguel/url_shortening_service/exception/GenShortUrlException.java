package dev.borriguel.url_shortening_service.exception;

public class GenShortUrlException extends RuntimeException {
    public GenShortUrlException(String message) {
        super(message);
    }
}
