package com.gujeducation.gujaratedu.Model;

public class Minami {
    int valueEducationId,mediumId;
    String date,madeBy,creditsAndLicence,title,thumbnail,video;

    public Minami(int valueEducationId, int mediumId, String date, String madeBy, String creditsAndLicence, String title, String thumbnail, String video) {
        this.valueEducationId = valueEducationId;
        this.mediumId = mediumId;
        this.date = date;
        this.madeBy = madeBy;
        this.creditsAndLicence = creditsAndLicence;
        this.title = title;
        this.thumbnail = thumbnail;
        this.video = video;
    }

    public int getValueEducationId() {
        return valueEducationId;
    }

    public void setValueEducationId(int valueEducationId) {
        this.valueEducationId = valueEducationId;
    }

    public int getMediumId() {
        return mediumId;
    }

    public void setMediumId(int mediumId) {
        this.mediumId = mediumId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMadeBy() {
        return madeBy;
    }

    public void setMadeBy(String madeBy) {
        this.madeBy = madeBy;
    }

    public String getCreditsAndLicence() {
        return creditsAndLicence;
    }

    public void setCreditsAndLicence(String creditsAndLicence) {
        this.creditsAndLicence = creditsAndLicence;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
