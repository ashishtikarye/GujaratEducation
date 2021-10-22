package com.gujeducation.gujaratedu.Model;

public class Evalution {
    int evalutionId, mediumId, standardId, semesterId;
    String patrak, pdf, pdf_name;

    public Evalution(int evalutionId, int mediumId, int standardId, int semesterId, String patrak, String pdf, String pdf_name) {
        this.evalutionId = evalutionId;
        this.mediumId = mediumId;
        this.standardId = standardId;
        this.semesterId = semesterId;
        this.patrak = patrak;
        this.pdf = pdf;
        this.pdf_name = pdf_name;
    }

    public int getEvalutionId() {
        return evalutionId;
    }

    public void setEvalutionId(int evalutionId) {
        this.evalutionId = evalutionId;
    }

    public int getMediumId() {
        return mediumId;
    }

    public void setMediumId(int mediumId) {
        this.mediumId = mediumId;
    }

    public int getStandardId() {
        return standardId;
    }

    public void setStandardId(int standardId) {
        this.standardId = standardId;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public String getPatrak() {
        return patrak;
    }

    public void setPatrak(String patrak) {
        this.patrak = patrak;
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
}
