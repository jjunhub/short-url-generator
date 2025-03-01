package demo.slm.common.infrastructure;

import demo.slm.common.service.CacheService;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CacheServiceImpl implements CacheService {

    private final Cache urlMapperCache;
    private final Cache clickCountCache;
    private final Cache clickCountKeysCache;
    private final RedisTemplate redisTemplate;

    public CacheServiceImpl(CacheManager cacheManager, RedisTemplate redisTemplate) {
        this.urlMapperCache = cacheManager.getCache("urlCache");
        this.clickCountCache = cacheManager.getCache("clickCache");
        this.clickCountKeysCache = cacheManager.getCache("clickCountKeys");
        this.redisTemplate = redisTemplate;
    }

    private String get(String key) {
        return Optional.ofNullable(urlMapperCache)
                .map(cache -> cache.get(key, String.class))
                .orElse(null);
    }

    @Override
    public void put(String shortUrlId, String originalUrl) {
        Optional.ofNullable(urlMapperCache)
                .ifPresent(cache -> cache.put(shortUrlId, originalUrl));
    }

    @Override
    @Async
    public void incrementClickCount(String shortUrlId) {
        Optional.ofNullable(redisTemplate)
                .ifPresent(template -> {
                    Long count = template.opsForValue().increment("clickCache::"+shortUrlId);
                    log.info("Click count for {} is {}", shortUrlId, count);
                });
        addClickCacheKey(shortUrlId);
    }

    @Override
    public String getOriginalUrlOrElseUpdate(String shortUrlId, Supplier<String> dbLookUp) {
        return Optional.ofNullable(get(shortUrlId))
                .orElseGet(() -> {
                    String originalUrl = dbLookUp.get();
                    put(shortUrlId, originalUrl);
                    return originalUrl;
                });
    }

    private void addClickCacheKey(String shortUrlId) {
        Set<String> keys = Optional.ofNullable(clickCountKeysCache.get("keys", Set.class)).orElse(new HashSet<>());
        keys.add(shortUrlId);
        clickCountKeysCache.put("keys", keys);
    }
}
