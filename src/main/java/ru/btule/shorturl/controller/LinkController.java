package ru.btule.shorturl.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.btule.shorturl.dto.LinkCreateDTO;
import ru.btule.shorturl.dto.LinkUpdateDTO;
import ru.btule.shorturl.exception.CustomExceptionHandler;
import ru.btule.shorturl.service.LinkService;

import java.net.URI;

@RestController
@CustomExceptionHandler
@RequestMapping("/redirect")
public class LinkController {

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/{shortLink}")
    public ResponseEntity<Void> redirectLink(@PathVariable String shortLink) {
        String sourceLink = linkService.getSourceLinkByShortLink(shortLink);

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .location(URI.create(sourceLink))
                .build();
    }

    @PostMapping
    public ResponseEntity<String> createLink(@RequestBody LinkCreateDTO linkCreateDTO) {
        String shortLink = linkService.shorter(linkCreateDTO);
        return ResponseEntity.ok(shortLink);
    }

    @DeleteMapping("/{shortLink}")
    public ResponseEntity<Void> deleteLink(@PathVariable String shortLink) {
        linkService.deleteLink(shortLink);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateLink(@RequestBody LinkUpdateDTO linkUpdateDTO) {
        linkService.updateLink(linkUpdateDTO);
        return ResponseEntity.ok().build();
    }
}