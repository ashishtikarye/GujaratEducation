package com.gujeducation.gujaratedu.Model;

public class PrePrimaryTextbook {
    int id,chapterId;
    String medium_title,common_title,image,medium_audio,common_audio;

    public PrePrimaryTextbook(int id, int chapterId, String medium_title, String common_title, String image, String medium_audio, String common_audio) {
        this.id = id;
        this.chapterId = chapterId;
        this.medium_title = medium_title;
        this.common_title = common_title;
        this.image = image;
        this.medium_audio = medium_audio;
        this.common_audio = common_audio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getMedium_title() {
        return medium_title;
    }

    public void setMedium_title(String medium_title) {
        this.medium_title = medium_title;
    }

    public String getCommon_title() {
        return common_title;
    }

    public void setCommon_title(String common_title) {
        this.common_title = common_title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMedium_audio() {
        return medium_audio;
    }

    public void setMedium_audio(String medium_audio) {
        this.medium_audio = medium_audio;
    }

    public String getCommon_audio() {
        return common_audio;
    }

    public void setCommon_audio(String common_audio) {
        this.common_audio = common_audio;
    }
}
