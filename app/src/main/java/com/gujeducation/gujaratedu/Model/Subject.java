package com.gujeducation.gujaratedu.Model;

public class Subject {
    int subjectId;
    int sectionId;
    int semesterId;
    int standardId;
    String subjectName;
    String semesterTag;
    String madeBy;
    String creditsAndLicence;
    String GcrtImage;

    public Subject(int subjectId, int sectionId, int semesterId, int standardId, String subjectName, String semesterTag, String madeBy, String creditsAndLicence, String gcrtImage) {
        this.subjectId = subjectId;
        this.sectionId = sectionId;
        this.semesterId = semesterId;
        this.standardId = standardId;
        this.subjectName = subjectName;
        this.semesterTag = semesterTag;
        this.madeBy = madeBy;
        this.creditsAndLicence = creditsAndLicence;
        GcrtImage = gcrtImage;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
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

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSemesterTag() {
        return semesterTag;
    }

    public void setSemesterTag(String semesterTag) {
        this.semesterTag = semesterTag;
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

    public String getGcrtImage() {
        return GcrtImage;
    }

    public void setGcrtImage(String gcrtImage) {
        GcrtImage = gcrtImage;
    }
}
