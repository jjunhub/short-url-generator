package demo.slm.mock;

import demo.slm.common.service.UrlGenerator;

public class FakeUrlGenerator implements UrlGenerator {

    private String shortUrlIdValue;
    private Integer count = 0;

    public FakeUrlGenerator(String shortUrlIdValue) {
        this.shortUrlIdValue = shortUrlIdValue;
    }

    @Override
    public String generateShortUrlId(String originalUrl) {
        return shortUrlIdValue + ++count;
    }
}
