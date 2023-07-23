package ru.btule.shorturl.dto;

import java.time.LocalDate;

public class LinkDTO {

    private String sourceLink;

    private LocalDate dateOfExpire;

    private boolean immortality;

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

    public boolean isImmortality() {
        return immortality;
    }

    public void setImmortality(boolean immortality) {
        this.immortality = immortality;
    }
}
