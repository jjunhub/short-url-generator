package demo.slm.common.service;

import java.util.function.Supplier;

public interface CacheService {
    void put(String shortUrlId, String originalUrl);
    void incrementClickCount(String shortUrlId);
    String getOriginalUrlOrElseUpdate(String shortUrlId, Supplier<String> dbLookUp);
}
