package ru.btule.shorturl.dto;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.time.LocalDate;

public class LinkUpdateDTO {

    private String sourceLink;

    private String shortLink;

    private LocalDate dateOfExpire;

    private Boolean isExpired;

    public String getSourceLink() {
        return sourceLink;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }

    public LocalDate getDateOfExpire() {
        return dateOfExpire;
    }

    public void setDateOfExpire(LocalDate dateOfExpire) {
        this.dateOfExpire = dateOfExpire;
    }

    public Boolean isExpired() {
        return isExpired;
    }

    @JsonSetter("isExpired")
    public void setExpired(Boolean expired) {
        isExpired = expired;
    }

    public String getShortLink() {
        return shortLink;
    }

    public void setShortLink(String shortLink) {
        this.shortLink = shortLink;
    }
}
