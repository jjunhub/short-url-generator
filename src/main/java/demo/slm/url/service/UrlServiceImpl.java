package demo.slm.url.service;

import demo.slm.common.service.CacheService;
import demo.slm.common.service.UrlGenerator;
import demo.slm.url.controller.port.UrlService;
import demo.slm.url.domain.ClickHistory;
import demo.slm.url.domain.ShortUrl;
import demo.slm.url.domain.ShortUrlCreate;
import demo.slm.url.service.port.ClickHistoryRepository;
import demo.slm.url.service.port.ShortUrlRepository;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class UrlServiceImpl implements UrlService {

    private final ShortUrlRepository shortUrlRepository;
    private final ClickHistoryRepository clickHistoryRepository;
    private final UrlGenerator urlGenerator;
    private final CacheService cacheService;

    public UrlServiceImpl(ShortUrlRepository shortUrlRepository, ClickHistoryRepository clickHistoryRepository,
                          UrlGenerator urlGenerator, CacheService cacheService) {
        this.shortUrlRepository = shortUrlRepository;
        this.clickHistoryRepository = clickHistoryRepository;
        this.urlGenerator = urlGenerator;
        this.cacheService = cacheService;
    }

    @Override
    public ShortUrl createShortLink(ShortUrlCreate shortUrlCreate) {
        ShortUrl shortUrl;
        do {
            shortUrl = ShortUrl.generateShortUrlId(shortUrlCreate, urlGenerator);
        } while (shortUrlRepository.checkAlreadyExists(shortUrl));

        return shortUrlRepository.save(shortUrl);
    }

    @Override
    public ShortUrl searchShortLink(String shortUrlIdValue) {
        return shortUrlRepository.findByIdOrThrow(shortUrlIdValue);
    }

    @Override
    public String getOriginalLink(String shortUrlId) {

        String originalUrl = cacheService.getOriginalUrlOrElseUpdate(shortUrlId, () -> {
            ShortUrl shortUrl = shortUrlRepository.findByIdOrThrow(shortUrlId);
            return shortUrl.originalUrl();
        });

        cacheService.incrementClickCount(shortUrlId);
        return originalUrl;
    }

    @Override
    public ClickHistory searchDailyClickCount(String shortUrlId, LocalDate date) {
        return clickHistoryRepository.findByShortUrlIdAndDate(shortUrlId, date);
    }
}
