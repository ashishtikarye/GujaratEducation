package com.gujeducation.gujaratedu.Model;

public class OldPaper {
    int oldpaperId,mediumId,examId,papersId,subjectId;
    String oldPaperPDF,year,oldPaperPDFName;

    public OldPaper(int oldpaperId, int mediumId, int examId, int papersId, int subjectId, String year, String oldPaperPDF,String oldPaperPDFName) {
        this.oldpaperId = oldpaperId;
        this.mediumId = mediumId;
        this.examId = examId;
        this.papersId = papersId;
        this.subjectId = subjectId;
        this.year = year;
        this.oldPaperPDF = oldPaperPDF;
        this.oldPaperPDFName = oldPaperPDFName;
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

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getPapersId() {
        return papersId;
    }

    public void setPapersId(int papersId) {
        this.papersId = papersId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getOldPaperPDF() {
        return oldPaperPDF;
    }

    public void setOldPaperPDF(String oldPaperPDF) {
        this.oldPaperPDF = oldPaperPDF;
    }

    public String getOldPaperPDFName() {
        return oldPaperPDFName;
    }

    public void setOldPaperPDFName(String oldPaperPDFName) {
        this.oldPaperPDFName = oldPaperPDFName;
    }
}
