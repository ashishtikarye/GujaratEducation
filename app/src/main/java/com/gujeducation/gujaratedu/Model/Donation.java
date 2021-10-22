package com.gujeducation.gujaratedu.Model;

public class Donation {
    int donationId,mediumId;
    String donationName,madeBy,paymentLink,date,image;

    public Donation(int donationId, int mediumId, String donationName, String madeBy, String paymentLink, String date, String image) {
        this.donationId = donationId;
        this.mediumId = mediumId;
        this.donationName = donationName;
        this.madeBy = madeBy;
        this.paymentLink = paymentLink;
        this.date = date;
        this.image = image;
    }

    public int getDonationId() {
        return donationId;
    }

    public void setDonationId(int donationId) {
        this.donationId = donationId;
    }

    public int getMediumId() {
        return mediumId;
    }

    public void setMediumId(int mediumId) {
        this.mediumId = mediumId;
    }

    public String getDonationName() {
        return donationName;
    }

    public void setDonationName(String donationName) {
        this.donationName = donationName;
    }

    public String getMadeBy() {
        return madeBy;
    }

    public void setMadeBy(String madeBy) {
        this.madeBy = madeBy;
    }

    public String getPaymentLink() {
        return paymentLink;
    }

    public void setPaymentLink(String paymentLink) {
        this.paymentLink = paymentLink;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
