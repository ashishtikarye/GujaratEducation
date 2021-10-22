package com.gujeducation.gujaratedu.Model;

public class Video {
    int studyvideoId,mediumId,examId,papersId,subjectId,chapterId;
    String madeBy,credits,title,thumbnail,videoLink;

    public Video(int studyvideoId, int mediumId, int examId, int papersId, int subjectId, int chapterId, String madeBy, String credits, String title, String thumbnail, String videoLink) {
        this.studyvideoId = studyvideoId;
        this.mediumId = mediumId;
        this.examId = examId;
        this.papersId = papersId;
        this.subjectId = subjectId;
        this.chapterId = chapterId;
        this.madeBy = madeBy;
        this.credits = credits;
        this.title = title;
        this.thumbnail = thumbnail;
        this.videoLink = videoLink;
    }

    public int getStudyvideoId() {
        return studyvideoId;
    }

    public void setStudyvideoId(int studyvideoId) {
        this.studyvideoId = studyvideoId;
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

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getMadeBy() {
        return madeBy;
    }

    public void setMadeBy(String madeBy) {
        this.madeBy = madeBy;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }
}
