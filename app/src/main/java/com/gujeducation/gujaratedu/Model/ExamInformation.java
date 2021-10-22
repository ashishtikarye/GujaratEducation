package com.gujeducation.gujaratedu.Model;

public class ExamInformation {

    int examInfoId, mediumId, examId;
    String topic, pdf;

    public ExamInformation(int examInfoId, int mediumId, int examId, String topic, String pdf) {
        this.examInfoId = examInfoId;
        this.mediumId = mediumId;
        this.examId = examId;
        this.topic = topic;
        this.pdf = pdf;
    }

    public int getExamInfoId() {
        return examInfoId;
    }

    public void setExamInfoId(int examInfoId) {
        this.examInfoId = examInfoId;
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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }
}
