package com.gujeducation.gujaratedu.Model;

public class Chapter {
    int chapterId,semesterId,subjectId;
    String chapterNo,chapterName,chapterPDF,chapterPDFName;

    public Chapter(int chapterId, int semesterId, String chapterNo, int subjectId, String chapterName) {
        this.chapterId = chapterId;
        this.semesterId = semesterId;
        this.chapterNo = chapterNo;
        this.subjectId = subjectId;
        this.chapterName = chapterName;

    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public String getChapterNo() {
        return chapterNo;
    }

    public void setChapterNo(String chapterNo) {
        this.chapterNo = chapterNo;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

}
