package ru.btule.shorturl.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.btule.shorturl.dto.LinkCreateDTO;
import ru.btule.shorturl.dto.LinkUpdateDTO;
import ru.btule.shorturl.exception.BadUrlException;
import ru.btule.shorturl.exception.EmptyParamException;
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

    private static final Logger log = LoggerFactory.getLogger(LinkService.class);
    private final LinkRepository linkRepository;
    private final ModelMapper modelMapper;

    public LinkService(LinkRepository linkRepository, ModelMapper modelMapper) {
        this.linkRepository = linkRepository;
        this.modelMapper = modelMapper;
    }

    public String shorter(LinkCreateDTO linkCreateDTO) {

        String sourceLink = linkCreateDTO.getSourceLink();
        LocalDate expireDate = linkCreateDTO.getDateOfExpire();
        boolean isExpired = true;

        if (linkCreateDTO.isExpired() != null) {
            isExpired = linkCreateDTO.isExpired();
        }


        validateSourceLink(sourceLink);

        String shortLink = RandomStringUtils.randomAlphanumeric(10);

        LinkEntity link = new LinkEntity();
        link.setShortLink(shortLink);
        link.setSourceLink(sourceLink);
        link.setCreateDate(LocalDate.now());
        if (isExpired) {
            link.setExpired(true);
            link.setExpireDate(Optional.ofNullable(expireDate).orElse(LocalDate.now().plusDays(2)));
        } else {
            link.setExpireDate(null);
            link.setExpired(false);
        }

        linkRepository.save(link);
        return shortLink;
    }

    private void validateSourceLink(String sourceLink) {
        if (sourceLink.length() == 0) {
            throw new InvalidParameterException("Ссылка не может быть пустой");
        }

        if (!isValidUrl(sourceLink)) {
            throw new BadUrlException("Ссылка недействительна, попробуй другую");
        }
    }


    public String getSourceLinkByShortLink(String shortLink) {
        Optional<LinkEntity> linkEntityOptional = linkRepository
                .findByShortLinkAndExpireDateAfterAndIsDeletedFalse(shortLink, LocalDate.now());

        return linkEntityOptional.map(LinkEntity::getSourceLink)
                .orElseThrow(() -> new NotFoundException("Ссылка отсутствует"));
    }

    private boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException exception) {
            log.warn("Ошибка при обработке URL: [{}]", url, exception);
            return false;
        }
    }

    public void deleteLink(String shortLink) {
        Optional<LinkEntity> optionalLink = linkRepository.findByShortLink(shortLink);
        LinkEntity linkEntity = optionalLink.orElseThrow(() -> new NotFoundException("Ссылка отсутствует"));
        linkEntity.setDeleted(true);
        linkRepository.save(linkEntity);
    }

    public void updateLink(LinkUpdateDTO linkUpdateDTO) {
        Optional<LinkEntity> optionalLink = linkRepository.findByShortLink(linkUpdateDTO.getShortLink());
        LinkEntity linkEntity = optionalLink.orElseThrow(() -> new NotFoundException("Ссылка отсутствует"));

        if (linkUpdateDTO.isExpired() == null) {
            throw new EmptyParamException("Вы не указали параметр isExpired");
        }

        linkEntity.setExpired(linkUpdateDTO.isExpired());

        validateSourceLink(linkUpdateDTO.getSourceLink());

        linkEntity.setSourceLink(linkUpdateDTO.getSourceLink());
        linkEntity.setExpireDate(linkUpdateDTO.getDateOfExpire());
        linkRepository.save(linkEntity);
    }
}