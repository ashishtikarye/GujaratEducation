package com.gujeducation.gujaratedu.Model;

public class OldPaperSubject {
    int oldpaperId,mediumId,sectionId,semesterId,standardId,subjectId;
    String oldPaperPDF,year,oldPaperPDFName,date;

    public OldPaperSubject(int oldpaperId, int mediumId, int sectionId, int semesterId, int standardId, int subjectId, String oldPaperPDF, String year, String oldPaperPDFName, String date) {
        this.oldpaperId = oldpaperId;
        this.mediumId = mediumId;
        this.sectionId = sectionId;
        this.semesterId = semesterId;
        this.standardId = standardId;
        this.subjectId = subjectId;
        this.oldPaperPDF = oldPaperPDF;
        this.year = year;
        this.oldPaperPDFName = oldPaperPDFName;
        this.date = date;
    }

    public int getOldpaperId() {
        return oldpaperId;
    }

    public void setOldpaperId(int oldpaperId) {
        this.oldpaperId = oldpaperId;
    }

    public int getMediumId() {
        return mediumId;
    }

    public void setMediumId(int mediumId) {
        this.mediumId = mediumId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public int getStandardId() {
        return standardId;
    }

    public void setStandardId(int standardId) {
        this.standardId = standardId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getOldPaperPDF() {
        return oldPaperPDF;
    }

    public void setOldPaperPDF(String oldPaperPDF) {
        this.oldPaperPDF = oldPaperPDF;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getOldPaperPDFName() {
        return oldPaperPDFName;
    }

    public void setOldPaperPDFName(String oldPaperPDFName) {
        this.oldPaperPDFName = oldPaperPDFName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
