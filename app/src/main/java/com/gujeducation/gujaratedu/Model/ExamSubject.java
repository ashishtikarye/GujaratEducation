package com.gujeducation.gujaratedu.Model;

public class ExamSubject {
    int mediumId, examId, papersId, subjectId;
    String subjectName, madeBy, creditsAndLicence, subjectImage;

    public ExamSubject(int mediumId, int examId, int papersId, int subjectId, String subjectName, String madeBy, String creditsAndLicence, String subjectImage) {
        this.mediumId = mediumId;
        this.examId = examId;
        this.papersId = papersId;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.madeBy = madeBy;
        this.creditsAndLicence = creditsAndLicence;
        this.subjectImage = subjectImage;
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

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
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

    public String getSubjectImage() {
        return subjectImage;
    }

    public void setSubjectImage(String subjectImage) {
        this.subjectImage = subjectImage;
    }
}
