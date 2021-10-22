package com.gujeducation.gujaratedu.Model;

public class ImportantLinks {
    int impLinksId;
    int mediumId;
    String impLinksName;
    String impLinksBy;
    String impLinksCredits;
    String impLinksImage;

    public ImportantLinks(int impLinksId, int mediumId, String impLinksName, String impLinksBy, String impLinksCredits, String impLinksImage) {
        this.impLinksId = impLinksId;
        this.mediumId = mediumId;
        this.impLinksName = impLinksName;
        this.impLinksBy = impLinksBy;
        this.impLinksCredits = impLinksCredits;
        this.impLinksImage = impLinksImage;
    }

    public int getImpLinksId() {
        return impLinksId;
    }

    public void setImpLinksId(int impLinksId) {
        this.impLinksId = impLinksId;
    }

    public int getMediumId() {
        return mediumId;
    }

    public void setMediumId(int mediumId) {
        this.mediumId = mediumId;
    }

    public String getImpLinksName() {
        return impLinksName;
    }

    public void setImpLinksName(String impLinksName) {
        this.impLinksName = impLinksName;
    }

    public String getImpLinksBy() {
        return impLinksBy;
    }

    public void setImpLinksBy(String impLinksBy) {
        this.impLinksBy = impLinksBy;
    }

    public String getImpLinksCredits() {
        return impLinksCredits;
    }

    public void setImpLinksCredits(String impLinksCredits) {
        this.impLinksCredits = impLinksCredits;
    }

    public String getImpLinksImage() {
        return impLinksImage;
    }

    public void setImpLinksImage(String impLinksImage) {
        this.impLinksImage = impLinksImage;
    }
}
