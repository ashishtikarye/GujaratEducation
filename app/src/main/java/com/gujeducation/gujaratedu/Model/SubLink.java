package com.gujeducation.gujaratedu.Model;

/**
 * Created by Tikarye Ashish on 17/09/2019.
 */

public class SubLink {
    int subLinkId;
    int impLinkId;
    String subLinkName;
    String subLinkPDF;

    public SubLink(int subLinkId, int impLinkId, String subLinkName, String subLinkPDF) {
        this.subLinkId = subLinkId;
        this.impLinkId = impLinkId;
        this.subLinkName = subLinkName;
        this.subLinkPDF = subLinkPDF;
    }

    public int getSubLinkId() {
        return subLinkId;
    }

    public void setSubLinkId(int subLinkId) {
        this.subLinkId = subLinkId;
    }

    public int getImpLinkId() {
        return impLinkId;
    }

    public void setImpLinkId(int impLinkId) {
        this.impLinkId = impLinkId;
    }

    public String getSubLinkName() {
        return subLinkName;
    }

    public void setSubLinkName(String subLinkName) {
        this.subLinkName = subLinkName;
    }

    public String getSubLinkPDF() {
        return subLinkPDF;
    }

    public void setSubLinkPDF(String subLinkPDF) {
        this.subLinkPDF = subLinkPDF;
    }
}
