package demo.slm.url.infrastructure;

import demo.slm.url.domain.ClickHistory;
import demo.slm.url.infrastructure.persistence.ClickHistoryEntity;
import demo.slm.url.infrastructure.persistence.ClickHistoryJpaRepository;
import demo.slm.url.service.port.ClickHistoryRepository;
import java.time.LocalDate;
import org.springframework.stereotype.Repository;

@Repository
public class ClickHistoryRepositoryImpl implements ClickHistoryRepository {

    private final ClickHistoryJpaRepository clickHistoryJpaRepository;

    public ClickHistoryRepositoryImpl(ClickHistoryJpaRepository clickHistoryJpaRepository) {
        this.clickHistoryJpaRepository = clickHistoryJpaRepository;
    }

    @Override
    public void bulkSave(String shortUrlId, Integer clickCount, LocalDate clickDate) {
        clickHistoryJpaRepository.bulkSave(shortUrlId, clickCount, clickDate);
    }

    @Override
    public ClickHistory findByShortUrlIdAndDate(String shortUrlId, LocalDate date) {
        return clickHistoryJpaRepository.findByShortUrlIdAndClickDate(shortUrlId, date)
                .map(ClickHistoryEntity::toDomain)
                .orElseGet(() -> ClickHistory.builder()
                        .shortUrlId(shortUrlId)
                        .date(date)
                        .clickCount(0L)
                        .build());
    }

}
