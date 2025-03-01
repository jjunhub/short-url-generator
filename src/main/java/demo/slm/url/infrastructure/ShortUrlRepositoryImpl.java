package demo.slm.url.infrastructure;

import demo.slm.url.domain.ShortUrl;
import demo.slm.url.infrastructure.persistence.ShortUrlEntity;
import demo.slm.url.infrastructure.persistence.ShortUrlJpaRepository;
import demo.slm.url.service.port.ShortUrlRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ShortUrlRepositoryImpl implements ShortUrlRepository {

    private final ShortUrlJpaRepository shortUrlJpaRepository;

    public ShortUrlRepositoryImpl(ShortUrlJpaRepository shortUrlJpaRepository) {
        this.shortUrlJpaRepository = shortUrlJpaRepository;
    }

    public ShortUrl findByIdOrThrow(String shortUrlIdValue) {
        return shortUrlJpaRepository.findById(shortUrlIdValue)
                .map(ShortUrlEntity::toDomain)
                .orElseThrow(() -> new RuntimeException("ShortUrl not found"));
    }

    @Override
    public boolean checkAlreadyExists(ShortUrl shortUrl) {
        return shortUrlJpaRepository.existsById(shortUrl.shortUrlId());
    }

    @Override
    public ShortUrl save(ShortUrl shortUrl) {
        ShortUrlEntity savedEntity = shortUrlJpaRepository.save(ShortUrlEntity.fromDomain(shortUrl));
        return savedEntity.toDomain();
    }
}
