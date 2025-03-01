package demo.slm.url.domain;

import lombok.Builder;

@Builder
public record ShortUrlCreate(
        String url
) {
}
