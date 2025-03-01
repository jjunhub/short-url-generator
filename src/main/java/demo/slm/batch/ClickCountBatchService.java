package demo.slm.batch;

import demo.slm.url.service.port.ClickHistoryRepository;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Profile("batch")
@Slf4j
public class ClickCountBatchService {
    private final Cache clickCountCache;
    private final Cache clickCountKeysCache;
    private final ClickHistoryRepository clickHistoryRepository;

    public ClickCountBatchService(CacheManager cacheManager, ClickHistoryRepository clickHistoryRepository) {
        this.clickCountCache = cacheManager.getCache("clickCache");
        this.clickCountKeysCache = cacheManager.getCache("clickCountKeys");
        this.clickHistoryRepository = clickHistoryRepository;
    }

    @Scheduled(fixedRate = 1000 * 60)
    @Transactional
    public void batchUpdateClickCount() {
        Set<String> keys = getClickCountKeys();

        if (keys.isEmpty()) {
            return;
        }

        LocalDate clickDate = LocalDate.now();
        keys.forEach(shortUrlId -> {
            Integer count = clickCountCache.get(shortUrlId, Integer.class);
            if (count != null) {
                clickHistoryRepository.bulkSave(shortUrlId, count, clickDate);
                log.info("Click count {} for {} saved to history", count, shortUrlId);
                Integer currentCount = clickCountCache.get(shortUrlId, Integer.class);
                if (currentCount != null) {
                    clickCountCache.put(shortUrlId, currentCount - count);
                }
            }
        });

        clickCountKeysCache.evict("keys");
    }

    private Set<String> getClickCountKeys() {
        return Optional.ofNullable(clickCountKeysCache.get("keys", Set.class)).orElse(new HashSet<>());
    }

}
