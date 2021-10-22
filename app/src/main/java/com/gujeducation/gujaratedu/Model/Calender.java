package com.gujeducation.gujaratedu.Model;

public class Calender {
    int calendarId,mediumId;
    String calendarName,coverImage,month,image,madeBy,creditAndLicence;

    public Calender(int calendarId, int mediumId, String calendarName, String coverImage, String month, String image, String madeBy, String creditAndLicence) {
        this.calendarId = calendarId;
        this.mediumId = mediumId;
        this.calendarName = calendarName;
        this.coverImage = coverImage;
        this.month = month;
        this.image = image;
        this.madeBy = madeBy;
        this.creditAndLicence = creditAndLicence;
    }

    public int getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(int calendarId) {
        this.calendarId = calendarId;
    }

    public int getMediumId() {
        return mediumId;
    }

    public void setMediumId(int mediumId) {
        this.mediumId = mediumId;
    }

    public String getCalendarName() {
        return calendarName;
    }

    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
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
