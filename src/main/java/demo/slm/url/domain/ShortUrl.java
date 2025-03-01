package demo.slm.url.domain;

import demo.slm.common.service.UrlGenerator;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ShortUrl(
        String shortUrlId,
        String originalUrl,
        LocalDateTime createdAt
) {
    public static ShortUrl generateShortUrlId(ShortUrlCreate shortUrlCreate, UrlGenerator urlGenerator) {
        return ShortUrl.builder()
                .shortUrlId(urlGenerator.generateShortUrlId(shortUrlCreate.url()))
                .originalUrl(shortUrlCreate.url())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
