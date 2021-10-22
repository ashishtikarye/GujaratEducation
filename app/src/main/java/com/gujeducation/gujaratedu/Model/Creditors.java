package com.gujeducation.gujaratedu.Model;

/**
 * Created by Tikarye Ashish on 17/09/2019.
 */

public class Creditors {
    int creditorsId;
    String creditorsTitle;
    String creditorsName;
    String creditorsDesignation;
    String creditorsDescription;

    public Creditors(int creditorsId, String creditorsTitle, String creditorsName, String creditorsDesignation, String creditorsDescription) {
        this.creditorsId = creditorsId;
        this.creditorsTitle = creditorsTitle;
        this.creditorsName = creditorsName;
        this.creditorsDesignation = creditorsDesignation;
        this.creditorsDescription = creditorsDescription;
    }

    public int getCreditorsId() {
        return creditorsId;
    }

    public void setCreditorsId(int creditorsId) {
        this.creditorsId = creditorsId;
    }

    public String getCreditorsTitle() {
        return creditorsTitle;
    }

    public void setCreditorsTitle(String creditorsTitle) {
        this.creditorsTitle = creditorsTitle;
    }

    public String getCreditorsName() {
        return creditorsName;
    }

    public void setCreditorsName(String creditorsName) {
        this.creditorsName = creditorsName;
    }

    public String getCreditorsDesignation() {
        return creditorsDesignation;
    }

    public void setCreditorsDesignation(String creditorsDesignation) {
        this.creditorsDesignation = creditorsDesignation;
    }

    public String getCreditorsDescription() {
        return creditorsDescription;
    }

    public void setCreditorsDescription(String creditorsDescription) {
        this.creditorsDescription = creditorsDescription;
    }
}
