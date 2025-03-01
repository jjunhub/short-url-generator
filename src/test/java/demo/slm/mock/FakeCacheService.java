package demo.slm.mock;

import demo.slm.common.service.CacheService;
import java.util.function.Supplier;

public class FakeCacheService implements CacheService {

    @Override
    public void put(String shortUrlId, String originalUrl) {

    }

    @Override
    public void incrementClickCount(String shortUrlId) {

    }

    @Override
    public String getOriginalUrlOrElseUpdate(String shortUrlId, Supplier<String> dbLookUp) {
        return "";
    }
}
