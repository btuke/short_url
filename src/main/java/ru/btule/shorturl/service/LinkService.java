package ru.btule.shorturl.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import ru.btule.shorturl.dto.LinkDTO;
import ru.btule.shorturl.exception.BadUrlException;
import ru.btule.shorturl.exception.NotFoundException;
import ru.btule.shorturl.model.LinkEntity;
import ru.btule.shorturl.repository.LinkRepository;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class LinkService {

    private final LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public String shorter(LinkDTO linkDTO) {

        String sourceLink = linkDTO.getSourceLink();
        LocalDate expireDate = linkDTO.getDateOfExpire();
        boolean immortality = linkDTO.isImmortality();


        if (sourceLink.length() == 0) {
            throw new InvalidParameterException("Ссылка не может быть пустой");
        }

        if (!isValidUrl(sourceLink)) {
            throw new BadUrlException("Ссылка недействительна, попробуй другую");
        }

        String shortLink = RandomStringUtils.randomAlphanumeric(10);

        LinkEntity link = new LinkEntity();
        link.setShortLink(shortLink);
        link.setSourceLink(sourceLink);
        link.setCreateDate(LocalDate.now());

        if (immortality) {
            link.setExpireDate(LocalDate.now().plusYears(100));
        } else {
            link.setExpireDate(Optional.ofNullable(expireDate).orElse(LocalDate.now().plusDays(2)));
        }
        linkRepository.save(link);

        return shortLink;
    }

    public String getSourceLinkByShortLink(String shortLink) {
        Optional<LinkEntity> linkEntityOptional = linkRepository.findByShortLinkAndExpireDateAfter(shortLink, LocalDate.now());

        return linkEntityOptional.map(LinkEntity::getSourceLink)
                .orElseThrow(() -> new NotFoundException("Ссылка отсутствует"));
    }

    private boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            System.out.println("Ошибка при обработке URL" + e.getMessage());
            return false;
        }
    }
}