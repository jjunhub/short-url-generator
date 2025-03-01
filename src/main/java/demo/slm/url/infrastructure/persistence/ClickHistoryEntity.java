package demo.slm.url.infrastructure.persistence;

import demo.slm.url.domain.ClickHistory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "click_history")
@IdClass(ClickHistoryEntityId.class)
public class ClickHistoryEntity {

    @Id
    @Column(name = "short_url_id", nullable = false, columnDefinition = "varchar(30)")
    private String shortUrlId;

    @Id
    @Column(name = "click_date", nullable = false, columnDefinition = "date")
    private LocalDate clickDate;

    @Column(name = "click_count", nullable = false, columnDefinition = "bigint")
    private Long clickCount;

    protected ClickHistoryEntity() {
    }

    public ClickHistory toDomain() {
        return ClickHistory.builder()
                .shortUrlId(shortUrlId)
                .date(clickDate)
                .clickCount(clickCount)
                .build();
    }
}
