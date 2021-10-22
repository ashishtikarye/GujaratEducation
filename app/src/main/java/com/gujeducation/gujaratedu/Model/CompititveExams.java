package com.gujeducation.gujaratedu.Model;

public class CompititveExams {
    int compititiveExamId, mediumId;
    String compititiveExamName, className, classImage;

    public CompititveExams(int compititiveExamId, int mediumId, String compititiveExamName, String className, String classImage) {
        this.compititiveExamId = compititiveExamId;
        this.mediumId = mediumId;
        this.compititiveExamName = compititiveExamName;
        this.className = className;
        this.classImage = classImage;
    }

    public int getCompititiveExamId() {
        return compititiveExamId;
    }

    public void setCompititiveExamId(int compititiveExamId) {
        this.compititiveExamId = compititiveExamId;
    }

    public int getMediumId() {
        return mediumId;
    }

    public void setMediumId(int mediumId) {
        this.mediumId = mediumId;
    }

    public String getCompititiveExamName() {
        return compititiveExamName;
    }

    public void setCompititiveExamName(String compititiveExamName) {
        this.compititiveExamName = compititiveExamName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassImage() {
        return classImage;
    }

    public void setClassImage(String classImage) {
        this.classImage = classImage;
    }
}
