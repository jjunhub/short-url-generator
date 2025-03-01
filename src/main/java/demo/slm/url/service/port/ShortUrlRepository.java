package demo.slm.url.service.port;

import demo.slm.url.domain.ShortUrl;

public interface ShortUrlRepository {
    ShortUrl findByIdOrThrow(String shortUrlId);

    boolean checkAlreadyExists(ShortUrl shortUrl);

    ShortUrl save(ShortUrl shortUrl);
}
