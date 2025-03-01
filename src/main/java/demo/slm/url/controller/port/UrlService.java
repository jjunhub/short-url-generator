package demo.slm.url.controller.port;

import demo.slm.url.domain.ClickHistory;
import demo.slm.url.domain.ShortUrl;
import demo.slm.url.domain.ShortUrlCreate;
import java.time.LocalDate;

public interface UrlService {
    ShortUrl createShortLink(ShortUrlCreate shortUrlCreate);

    ShortUrl searchShortLink(String shortUrlId);

    String getOriginalLink(String shortUrlId);

    ClickHistory searchDailyClickCount(String shortUrlId, LocalDate date);
}
