package demo.slm.url.infrastructure.persistence;

import demo.slm.url.domain.ShortUrl;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;

@Entity
@Table(name = "short_url")
public class ShortUrlEntity {

    @Id
    @Column(name = "short_url_id", nullable = false, unique = true, columnDefinition = "varchar(30)")
    private String shortUrlId;

    @Column(name = "original_url", nullable = false, columnDefinition = "varchar(2048)")
    private String originalUrl;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public ShortUrlEntity() {
    }

    @Builder
    public ShortUrlEntity(LocalDateTime createdAt, String originalUrl, String shortUrlId) {
        this.createdAt = createdAt;
        this.originalUrl = originalUrl;
        this.shortUrlId = shortUrlId;
    }

    public static ShortUrlEntity fromDomain(ShortUrl shortUrl) {
        return ShortUrlEntity.builder()
                .shortUrlId(shortUrl.shortUrlId())
                .originalUrl(shortUrl.originalUrl())
                .createdAt(shortUrl.createdAt())
                .build();
    }

    public ShortUrl toDomain() {
        return ShortUrl.builder()
                .shortUrlId(shortUrlId)
                .originalUrl(originalUrl)
                .createdAt(createdAt)
                .build();
    }
}
