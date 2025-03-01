package demo.slm.mock;

import demo.slm.url.service.port.ClickHistoryRepository;
import java.time.LocalDate;

public class FakeClickHistoryRepository implements ClickHistoryRepository {
    @Override
    public void bulkSave(String shortUrlId, Integer clickCount, LocalDate clickDate) {

    }
}
