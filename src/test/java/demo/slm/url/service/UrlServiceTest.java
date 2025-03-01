package demo.slm.url.service;

import static org.assertj.core.api.Assertions.assertThat;

import demo.slm.mock.FakeCacheService;
import demo.slm.mock.FakeClickHistoryRepository;
import demo.slm.mock.FakeShortUrlRepository;
import demo.slm.mock.FakeUrlGenerator;
import demo.slm.url.controller.port.UrlService;
import demo.slm.url.domain.ShortUrl;
import demo.slm.url.domain.ShortUrlCreate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UrlServiceTest {
    private UrlService urlService;

    @BeforeEach
    void setUp() {
        FakeShortUrlRepository fakeShortLinkRepository = new FakeShortUrlRepository();
        fakeShortLinkRepository.save(
                ShortUrl.builder()
                        .originalUrl("https://www.test.com")
                        .shortUrlId("shortUrlIdTest")
                        .build()
        );
        FakeClickHistoryRepository fakeClickHistoryRepository = new FakeClickHistoryRepository();
        FakeUrlGenerator fakeUrlGenerator = new FakeUrlGenerator("test1234");
        FakeCacheService fakeCacheService = new FakeCacheService();

        urlService = new UrlServiceImpl(fakeShortLinkRepository, fakeClickHistoryRepository, fakeUrlGenerator,
                fakeCacheService);
    }

    @DisplayName("사용자는 URL을 입력하여, 단축 URL을 생성할 수 있다.")
    @Test
    void createShortUrlId() {
        // Given
        ShortUrlCreate shortUrlCreate = ShortUrlCreate.builder()
                .url("https://jjunhub.tistory.com/10")
                .build();
        // When
        ShortUrl shortUrl = urlService.createShortLink(shortUrlCreate);

        System.out.println(shortUrl.shortUrlId());

        // Then
        assertThat(shortUrl).isNotNull()
                .extracting(ShortUrl::originalUrl, (s) -> s.shortUrlId().startsWith("test1234"))
                .contains("https://jjunhub.tistory.com/10", true);
    }

    @DisplayName("사용자는 shortUrlId를 입력하여, ShortLink에 대한 정보를 조회할 수 있다.")
    @Test
    void searchShortLink() {
        // Given
        // When
        ShortUrl shortUrl = urlService.searchShortLink("shortUrlIdTest");

        // Then
        assertThat(shortUrl).isNotNull()
                .extracting(ShortUrl::originalUrl, ShortUrl::shortUrlId)
                .contains("https://www.test.com", "shortUrlIdTest");
    }

    @DisplayName("사용자는 반복적으로 같은 URL을 입력하더라도, 다른 shortUrlId가 생성된다.")
    @Test
    void createUniqueshortUrlId() {
        // Given
        ShortUrlCreate shortUrlCreate = ShortUrlCreate.builder()
                .url("https://jjunhub.tistory.com/10")
                .build();

        // When
        List<String> shortLinks = new ArrayList<>();
        shortLinks.add(urlService.createShortLink(shortUrlCreate).shortUrlId());
        shortLinks.add(urlService.createShortLink(shortUrlCreate).shortUrlId());
        shortLinks.add(urlService.createShortLink(shortUrlCreate).shortUrlId());

        // Then
        assertThat(shortLinks).doesNotHaveDuplicates();
    }

}