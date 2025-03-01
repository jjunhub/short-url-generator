package demo.slm.url.infrastructure.persistence;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class ClickHistoryEntityId implements Serializable {
    private String shortUrlId;
    private LocalDate clickDate;

    protected ClickHistoryEntityId() {
    }

    public ClickHistoryEntityId(String shortUrlId, LocalDate clickDate) {
        this.shortUrlId = shortUrlId;
        this.clickDate = clickDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClickHistoryEntityId that = (ClickHistoryEntityId) o;
        return Objects.equals(shortUrlId, that.shortUrlId) && Objects.equals(clickDate, that.clickDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shortUrlId, clickDate);
    }

}
