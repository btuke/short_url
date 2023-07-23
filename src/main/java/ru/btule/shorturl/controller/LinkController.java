package ru.btule.shorturl.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.btule.shorturl.dto.LinkDTO;
import ru.btule.shorturl.exception.CustomExceptionHandler;
import ru.btule.shorturl.service.LinkService;

import java.net.URI;
import java.time.LocalDate;
import java.util.Optional;


@RestController
@CustomExceptionHandler
public class LinkController {

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/redirect/{shortLink}")
    public ResponseEntity<?> redirectLink(@PathVariable String shortLink) {
        String sourceLink = linkService.getSourceLinkByShortLink(shortLink);

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .location(URI.create(sourceLink))
                .build();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createLink(@RequestBody LinkDTO linkDTO) {
        String shortLink = linkService.shorter(linkDTO);
        return ResponseEntity.ok(shortLink);
    }
}



