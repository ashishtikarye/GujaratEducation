package com.gujeducation.gujaratedu.Model;

public class CalenderEdition {
    int calenderEditionId,calenderId;
    String months,madeBy,creditsAndLicence,pdf,pdf_name,coverImage;

    public CalenderEdition(int calenderEditionId, int calenderId, String months, String madeBy, String creditsAndLicence, String pdf, String pdf_name, String coverImage) {
        this.calenderEditionId = calenderEditionId;
        this.calenderId = calenderId;
        this.months = months;
        this.madeBy = madeBy;
        this.creditsAndLicence = creditsAndLicence;
        this.pdf = pdf;
        this.pdf_name = pdf_name;
        this.coverImage = coverImage;
    }

    public int getCalenderEditionId() {
        return calenderEditionId;
    }

    public void setCalenderEditionId(int calenderEditionId) {
        this.calenderEditionId = calenderEditionId;
    }

    public int getCalenderId() {
        return calenderId;
    }

    public void setCalenderId(int calenderId) {
        this.calenderId = calenderId;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
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

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getPdf_name() {
        return pdf_name;
    }

    public void setPdf_name(String pdf_name) {
        this.pdf_name = pdf_name;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
}
