package com.gujeducation.gujaratedu.Model;

/**
 * Created by Tikarye Ashish on 15/09/2019.
 */

public class Slider {
    int bannerId;
    int mediumId;
    String bannerTitle;
    String bannerUrl;
    String bannerImg;

    public Slider(int bannerId, int mediumId, String bannerTitle, String bannerUrl, String bannerImg) {
        this.bannerId = bannerId;
        this.mediumId = mediumId;
        this.bannerTitle = bannerTitle;
        this.bannerUrl = bannerUrl;
        this.bannerImg = bannerImg;
    }

    public int getBannerId() {
        return bannerId;
    }

    public void setBannerId(int bannerId) {
        this.bannerId = bannerId;
    }

    public int getMediumId() {
        return mediumId;
    }

    public void setMediumId(int mediumId) {
        this.mediumId = mediumId;
    }

    public String getBannerTitle() {
        return bannerTitle;
    }

    public void setBannerTitle(String bannerTitle) {
        this.bannerTitle = bannerTitle;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }
}
