package demo.slm.url.service.port;

import demo.slm.url.domain.ClickHistory;
import java.time.LocalDate;

public interface ClickHistoryRepository {
    void bulkSave(String shortUrlId, Integer clickCount, LocalDate clickDate);

    ClickHistory findByShortUrlIdAndDate(String shortUrlId, LocalDate date);
}
