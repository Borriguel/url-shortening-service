package dev.borriguel.url_shortening_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.borriguel.url_shortening_service.model.Url;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    @Query("select (count(u) > 0) from Url u where u.shortUrl = ?1")
    boolean existsByShortUrl(String shortUrl);

    @Query("select u from Url u where u.shortUrl = ?1")
    Optional<Url> findByShortUrl(String shortUrl);

    @Modifying
    @Query("UPDATE Url u SET u.accessCount = u.accessCount + 1 WHERE u.shortUrl = :shortUrl")
    void incrementAccessCount(@Param("shortUrl") String shortUrl);
}
