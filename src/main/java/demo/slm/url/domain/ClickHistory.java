package demo.slm.url.domain;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record ClickHistory (
        String shortUrlId,
        LocalDate date,
        Long clickCount
){
}
