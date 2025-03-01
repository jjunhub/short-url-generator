package demo.slm.common.service;

import static org.assertj.core.api.Assertions.assertThat;

import demo.slm.common.infrastructure.UrlGeneratorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UrlGeneratorTest {

    private UrlGenerator urlGenerator;

    @BeforeEach
    void setUp() {
        urlGenerator = new UrlGeneratorImpl();
    }

    @DisplayName("항상 7자리 shortUrlId가 생성된다.")
    @Test
    void generateSevenLengthshortUrlId() {
        // Given
        String originalUrl = "https://www.ab180.com";

        // When
        String shortUrlId = urlGenerator.generateShortUrlId(originalUrl);

        // Then
        assertThat(shortUrlId).hasSize(7);
    }

    @DisplayName("shortUrlId는 alphanumeric 문자로만 구성된다.")
    @Test
    void generateAlphanumericshortUrlId() {
        // Given
        String originalUrl = "https://www.ab180.com";

        // When
        String shortUrlId = urlGenerator.generateShortUrlId(originalUrl);

        // Then
        assertThat(shortUrlId).matches("[a-zA-Z0-9]+");
    }
}