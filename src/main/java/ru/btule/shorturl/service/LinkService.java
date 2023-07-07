package ru.btule.shorturl.service;

import org.springframework.stereotype.Service;
import ru.btule.shorturl.model.LinkEntity;
import ru.btule.shorturl.repository.LinkRepository;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class LinkService {

    private final LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public String shorter(String sourceLink) {
        if (sourceLink.length() == 0) {
            throw new InvalidParameterException("Wrong door");
        }
        String shortLink = UUID.randomUUID().toString().substring(0, 10);
        LinkEntity link = new LinkEntity();
        link.setShortLink(shortLink);
        link.setSourceLink(sourceLink);
        link.setCreateDate(LocalDate.now());
        linkRepository.save(link);
        return shortLink;
    }
}
///TODO поменять UUID (не совсем UUID) на адекватное (тоже самое без тире) NOT toString and deleting - ссылка из 40 символов / LIBRARY FROM STA$$Y Люблю этот цвет