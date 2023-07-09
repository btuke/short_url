package ru.btule.shorturl.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.btule.shorturl.dto.LinkDTO;
import ru.btule.shorturl.exception.NotFoundException;
import ru.btule.shorturl.service.LinkService;

import java.net.URI;

@RestController
public class LinkController {

    private final LinkService linkService;


    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/redirect/{shortLink}")
    public ResponseEntity<?> redirectLink(@PathVariable String shortLink) {
        try {
            String sourceLink = linkService.getSourceLinkByShortLink(shortLink);
            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                    .location(URI.create(sourceLink))
                    .build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public String createLink(@RequestBody LinkDTO linkDTO) {
        return linkService.shorter(linkDTO.getSourceLink());
    }
}
// TODO сделать проверку входящей ссылки LinkDTO c входящим sourceLink на соответствие URI стандарту


