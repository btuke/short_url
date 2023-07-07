package ru.btule.shorturl.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.btule.shorturl.dto.LinkDTO;
import ru.btule.shorturl.model.LinkEntity;
import ru.btule.shorturl.service.LinkService;

@RestController
public class LinkController {

    private final LinkService linkService;


    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

//    @GetMapping("/redirect/{shortLink}")
//    public String redirectLink ()

    @PostMapping("/create")
    public String createLink(@RequestBody LinkDTO linkDTO) {
        return linkService.shorter(linkDTO.getSourceLink());
    }
}
