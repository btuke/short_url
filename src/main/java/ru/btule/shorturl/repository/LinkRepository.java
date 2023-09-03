package ru.btule.shorturl.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.btule.shorturl.model.LinkEntity;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface LinkRepository extends CrudRepository<LinkEntity, Long> {
    Optional<LinkEntity> findByShortLinkAndExpireDateAfterAndIsDeletedFalse(String shortLink, LocalDate expireDate);


    Optional<LinkEntity> findByShortLink(String shortLink);
}