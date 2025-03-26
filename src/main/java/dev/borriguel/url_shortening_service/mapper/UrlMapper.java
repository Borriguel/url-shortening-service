package dev.borriguel.url_shortening_service.mapper;

import dev.borriguel.url_shortening_service.dto.UrlResponse;
import dev.borriguel.url_shortening_service.dto.UrlStatResponse;
import dev.borriguel.url_shortening_service.model.Url;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UrlMapper {
    UrlResponse urlToResponse(Url url);
    UrlStatResponse urlToStatResponse(Url url);
}
