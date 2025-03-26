package dev.borriguel.url_shortening_service.service;

import dev.borriguel.url_shortening_service.dto.UrlRequest;
import dev.borriguel.url_shortening_service.exception.GenShortUrlException;
import dev.borriguel.url_shortening_service.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import dev.borriguel.url_shortening_service.model.Url;
import dev.borriguel.url_shortening_service.repository.UrlRepository;
import java.security.SecureRandom;

@Service
public class UrlServiceImpl implements UrlService {
    private final UrlRepository repository;
    private static final SecureRandom random = new SecureRandom();
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int SHORT_URL_LENGTH = 6;

    public UrlServiceImpl(UrlRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Url createShortUrl(UrlRequest originalUrl) {
        var url = new Url();
        url.setUrl(originalUrl.url());
        url.setShortUrl(generateShortUrl());
        return repository.save(url);
    }

    @Override
    @Transactional
    public Url retrieveOriginalUrl(String originalUrl) {
        var url = getUrlByShortUrl(originalUrl);
        repository.incrementAccessCount(url.getShortUrl());
        return url;
    }

    @Override
    @Transactional
    public Url updateUrl(String shortUrl, UrlRequest urlUpdated) {
        var url = getUrlByShortUrl(shortUrl);
        if (url.getUrl().equals(urlUpdated.url())) {
            return url;
        }
        url.setUrl(urlUpdated.url());
        return repository.save(url);
    }

    @Override
    @Transactional
    public void deleteUrl(String shortUrl) {
        repository.delete(getUrlByShortUrl(shortUrl));
    }

    @Override
    public Url getUrlStats(String shortUrl) {
        return getUrlByShortUrl(shortUrl);
    }

    private Url getUrlByShortUrl(String shortUrl) {
        return repository.findByShortUrl(shortUrl).orElseThrow(() -> new NotFoundException("Short url "+ shortUrl + "not found"));
    }

    private String generateShortUrl() {
        int maxAttempts = 10;
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            StringBuilder shortUrl = new StringBuilder(SHORT_URL_LENGTH);
            for (int i = 0; i < 6; i++) {
                shortUrl.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
            }
            if (!repository.existsByShortUrl(shortUrl.toString())) {
                return shortUrl.toString();
            }
        }
        throw new GenShortUrlException("Failed to generate short url after " + maxAttempts + " attempts");
    }

}
