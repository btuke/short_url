package ru.btule.shorturl.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.btule.shorturl.model.LinkEntity;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface LinkRepository extends CrudRepository<LinkEntity, Long> {
    Optional<LinkEntity> findByShortLinkAndExpireDateAfter(String shortLink, LocalDate expireDate);
}