package com.gujeducation.gujaratedu.Model;

import android.content.Context;

import com.gujeducation.R;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class Essay {
    int essayId,semesterId,sectionId,mediumId,standardId,subjectId;
    String title, essayBody;

    public Essay(int essayId, int semesterId, int sectionId, int mediumId, int standardId, int subjectId, String title, String essayBody) {
        this.essayId = essayId;
        this.semesterId = semesterId;
        this.sectionId = sectionId;
        this.mediumId = mediumId;
        this.standardId = standardId;
        this.subjectId = subjectId;
        this.title = title;
        this.essayBody = essayBody;
    }

    public int getEssayId() {
        return essayId;
    }

    public void setEssayId(int essayId) {
        this.essayId = essayId;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public int getMediumId() {
        return mediumId;
    }

    public void setMediumId(int mediumId) {
        this.mediumId = mediumId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEssayBody() {
        return essayBody;
    }

    public void setEssayBody(String essayBody) {
        this.essayBody = essayBody;
    }
}
