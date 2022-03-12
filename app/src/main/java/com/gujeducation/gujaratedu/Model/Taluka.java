package com.gujeducation.gujaratedu.Model;

public class Taluka {
    int disttrictId;
    int talukaId;
    String talukaName;

    public Taluka(int disttrictId, int talukaId, String talukaName) {
        this.disttrictId = disttrictId;
        this.talukaId = talukaId;
        this.talukaName = talukaName;
    }

    public int getDisttrictId() {
        return disttrictId;
    }

    public void setDisttrictId(int disttrictId) {
        this.disttrictId = disttrictId;
    }

    public int getTalukaId() {
        return talukaId;
    }

    public void setTalukaId(int talukaId) {
        this.talukaId = talukaId;
    }

    public String getTalukaName() {
        return talukaName;
    }

    public void setTalukaName(String talukaName) {
        this.talukaName = talukaName;
    }
}
