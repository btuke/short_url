package ru.btule.shorturl.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.btule.shorturl.model.LinkEntity;

@Repository
public interface LinkRepository extends CrudRepository<LinkEntity, Long> {
}
