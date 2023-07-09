package ru.btule.shorturl.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import ru.btule.shorturl.exception.NotFoundException;
import ru.btule.shorturl.model.LinkEntity;
import ru.btule.shorturl.repository.LinkRepository;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class LinkService {

    private final LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public String shorter(String sourceLink) {
        if (sourceLink.length() == 0) {
            throw new InvalidParameterException("Ссылка не может быть пустой");
        }

        String shortLink = RandomStringUtils.randomAlphanumeric(10);

        LinkEntity link = new LinkEntity();
        link.setShortLink(shortLink);
        link.setSourceLink(sourceLink);
        link.setCreateDate(LocalDate.now());
        linkRepository.save(link);

        return shortLink;
    }

    public String getSourceLinkByShortLink(String shortLink) throws NotFoundException {
        Optional<LinkEntity> linkEntityOptional = linkRepository.findByShortLink(shortLink);
        return linkEntityOptional.map(LinkEntity::getSourceLink)
                .orElseThrow(() -> new NotFoundException("Ссылка отсутствует"));
    }
}