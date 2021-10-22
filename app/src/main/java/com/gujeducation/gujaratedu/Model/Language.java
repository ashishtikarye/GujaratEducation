package com.gujeducation.gujaratedu.Model;

public class Language {
    int languageId;
    String languageName;
    int status;
    int isDelete;

    public Language(int languageId, String languageName, int status, int isDelete) {
        this.languageId = languageId;
        this.languageName = languageName;
        this.status = status;
        this.isDelete = isDelete;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }
}
