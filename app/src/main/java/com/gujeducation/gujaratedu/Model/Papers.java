package com.gujeducation.gujaratedu.Model;

public class Papers {
    int paperId,mediumId,examId;
    String paperName;

    public Papers(int paperId, int mediumId, int examId, String paperName) {
        this.paperId = paperId;
        this.mediumId = mediumId;
        this.examId = examId;
        this.paperName = paperName;
    }

    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
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

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }
}
