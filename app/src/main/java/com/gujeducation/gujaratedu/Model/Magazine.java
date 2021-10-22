package com.gujeducation.gujaratedu.Model;

public class Magazine {
    int magazineId,mediumId;
    String title,coverImage,month,image,date,madeBy,creditAndLicence;

    public Magazine(int magazineId, int mediumId, String title, String coverImage, String month, String image, String date, String madeBy, String creditAndLicence) {
        this.magazineId = magazineId;
        this.mediumId = mediumId;
        this.title = title;
        this.coverImage = coverImage;
        this.month = month;
        this.image = image;
        this.date = date;
        this.madeBy = madeBy;
        this.creditAndLicence = creditAndLicence;
    }

    public int getMagazineId() {
        return magazineId;
    }

    public void setMagazineId(int magazineId) {
        this.magazineId = magazineId;
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

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
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

    public String getMadeBy() {
        return madeBy;
    }

    public void setMadeBy(String madeBy) {
        this.madeBy = madeBy;
    }

    public String getCreditAndLicence() {
        return creditAndLicence;
    }

    public void setCreditAndLicence(String creditAndLicence) {
        this.creditAndLicence = creditAndLicence;
    }
}
