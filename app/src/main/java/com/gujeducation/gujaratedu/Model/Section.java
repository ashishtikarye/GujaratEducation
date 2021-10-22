package com.gujeducation.gujaratedu.Model;

public class Section {
    int sectionId;
    int mediumId;
    int standardId;
    String standard;
    String section;

    public Section(int sectionId, int mediumId, int standardId, String standard, String section) {
        this.sectionId = sectionId;
        this.mediumId = mediumId;
        this.standardId = standardId;
        this.standard = standard;
        this.section = section;
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

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
