package demo.slm.url.infrastructure.persistence;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ClickHistoryJpaRepository extends JpaRepository<ClickHistoryEntity, ClickHistoryEntityId> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO click_history (click_date, short_url_id, click_count) " +
            "VALUES (:clickDate, :shortUrlId, :clickCount) " +
            "ON DUPLICATE KEY UPDATE click_count = click_count + :clickCount",
            nativeQuery = true)
    void bulkSave(String shortUrlId, Integer clickCount, LocalDate clickDate);

    Optional<ClickHistoryEntity> findByShortUrlIdAndClickDate(String shortUrlId, LocalDate date);
}
