package com.gujeducation.gujaratedu.Model;

public class NewsCircular {
    int newsCircularId,mediumId;
    String title,image,date;

    public NewsCircular(int newsCircularId, int mediumId, String title, String image, String date) {
        this.newsCircularId = newsCircularId;
        this.mediumId = mediumId;
        this.title = title;
        this.image = image;
        this.date = date;
    }

    public int getNewsCircularId() {
        return newsCircularId;
    }

    public void setNewsCircularId(int newsCircularId) {
        this.newsCircularId = newsCircularId;
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
