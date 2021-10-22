package com.gujeducation.gujaratedu.Model;

public class SubjectChapterCompExam {

    int chapterId,mediumId,examId,papersId,subjectId;
            String chapterNo,chapterName;

    public SubjectChapterCompExam(int chapterId, int mediumId, int examId, int papersId, int subjectId, String chapterNo, String chapterName) {
        this.chapterId = chapterId;
        this.mediumId = mediumId;
        this.examId = examId;
        this.papersId = papersId;
        this.subjectId = subjectId;
        this.chapterNo = chapterNo;
        this.chapterName = chapterName;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
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

    public String getChapterNo() {
        return chapterNo;
    }

    public void setChapterNo(String chapterNo) {
        this.chapterNo = chapterNo;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
}


