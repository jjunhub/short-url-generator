package demo.slm.url.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortUrlJpaRepository extends JpaRepository<ShortUrlEntity, String> {
}
