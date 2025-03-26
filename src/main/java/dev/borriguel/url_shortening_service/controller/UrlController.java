package dev.borriguel.url_shortening_service.controller;

import dev.borriguel.url_shortening_service.dto.UrlRequest;
import dev.borriguel.url_shortening_service.dto.UrlResponse;
import dev.borriguel.url_shortening_service.dto.UrlStatResponse;
import dev.borriguel.url_shortening_service.mapper.UrlMapper;
import dev.borriguel.url_shortening_service.service.UrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shorten")
@Tag(name = "URL Shortener", description = "Endpoints to manage shortened URLs")
public class UrlController {
    private final UrlService service;
    private final UrlMapper mapper;

    public UrlController(UrlService service, UrlMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new short URL", description = "Creates a new shortened URL from the provided original URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Short URL created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UrlResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)
    })
    public UrlResponse createUrl(@Valid @RequestBody UrlRequest originalUrl) {
        return mapper.urlToResponse(service.createShortUrl(originalUrl));
    }

    @GetMapping("/{shortUrl}")
    @Operation(summary = "Retrieve original URL",
            description = "Retrieves the original URL from the provided short URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Original URL retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UrlResponse.class))),
            @ApiResponse(responseCode = "404", description = "URL not found", content = @Content)
    })
    public UrlResponse getOriginalUrl(@Parameter(description = "Shortened URL", required = true) @PathVariable String shortUrl) {
        return mapper.urlToResponse(service.retrieveOriginalUrl(shortUrl));
    }

    @GetMapping("/{shortUrl}/stats")
    @Operation(summary = "Get URL statistics",
            description = "Retrieves statistics for the provided short URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "URL statistics retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UrlStatResponse.class))),
            @ApiResponse(responseCode = "404", description = "URL not found", content = @Content)
    })
    public UrlStatResponse getUrlStats(@Parameter(description = "Shortened URL", required = true) @PathVariable String shortUrl) {
        return mapper.urlToStatResponse(service.getUrlStats(shortUrl));
    }

    @PutMapping("/{shortUrl}")
    @Operation(summary = "Update original URL",
            description = "Updates the original URL for the provided short URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "URL updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UrlResponse.class))),
            @ApiResponse(responseCode = "404", description = "URL not found", content = @Content)
    })
    public UrlResponse updateUrl(@Parameter(description = "Shortened URL", required = true) @PathVariable String shortUrl, @Valid @RequestBody UrlRequest updatedUrl) {
        return mapper.urlToResponse(service.updateUrl(shortUrl, updatedUrl));
    }

    @DeleteMapping("/{shortUrl}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete short URL",
            description = "Deletes the provided short URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "URL deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "URL not found", content = @Content)
    })
    public void deleteUrl(@Parameter(description = "Shortened URL", required = true) @PathVariable String shortUrl) {
        service.deleteUrl(shortUrl);
    }
}