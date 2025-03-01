package demo.slm.mock;

import demo.slm.url.domain.ShortUrl;
import demo.slm.url.service.port.ShortUrlRepository;
import java.util.ArrayList;
import java.util.List;

public class FakeShortUrlRepository implements ShortUrlRepository {

    private List<ShortUrl> data = new ArrayList<>();

    @Override
    public ShortUrl findByIdOrThrow(String shortUrlId) {
        return data.stream()
                .filter(sl -> sl.shortUrlId().equals(shortUrlId))
                .findAny()
                .orElseThrow(() -> new RuntimeException("ShortUrl not found"));
    }

    @Override
    public boolean checkAlreadyExists(ShortUrl shortUrl) {
        return data.stream()
                .anyMatch(sl -> sl.shortUrlId().equals(shortUrl.shortUrlId()));
    }

    @Override
    public ShortUrl save(ShortUrl shortUrl) {
        if (checkAlreadyExists(shortUrl)) {
            data.removeIf(sl -> sl.shortUrlId().equals(shortUrl.shortUrlId()));
        }
        data.add(shortUrl);
        return shortUrl;
    }
}
