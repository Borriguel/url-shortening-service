package dev.borriguel.url_shortening_service.service;

import dev.borriguel.url_shortening_service.dto.UrlRequest;
import dev.borriguel.url_shortening_service.model.Url;

public interface UrlService {
    Url createShortUrl(UrlRequest originalUrl);

    Url retrieveOriginalUrl(String originalUrl);

    Url updateUrl(String shortUrl, UrlRequest updatedUrl);

    void deleteUrl(String shortUrl);

    Url getUrlStats(String originalUrl);
}
