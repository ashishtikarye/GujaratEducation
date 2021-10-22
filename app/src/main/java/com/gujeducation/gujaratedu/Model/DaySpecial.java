package com.gujeducation.gujaratedu.Model;

public class DaySpecial {
    int daySpecialId,mediumId;
    String title,subtitle,description,image,date;

    public DaySpecial(int daySpecialId, int mediumId, String title, String subtitle, String description, String image, String date) {
        this.daySpecialId = daySpecialId;
        this.mediumId = mediumId;
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.image = image;
        this.date = date;
    }

    public int getDaySpecialId() {
        return daySpecialId;
    }

    public void setDaySpecialId(int daySpecialId) {
        this.daySpecialId = daySpecialId;
    }

    public int getMediumId() {
        return mediumId;
    }

    public void setMediumId(int mediumId) {
        this.mediumId = mediumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
