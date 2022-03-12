package com.gujeducation.gujaratedu.Model;

public class District {
    int disttrictId;
    String disttrictName;

    public District(int disttrictId, String disttrictName) {
        this.disttrictId = disttrictId;
        this.disttrictName = disttrictName;
    }

    public int getDisttrictId() {
        return disttrictId;
    }

    public void setDisttrictId(int disttrictId) {
        this.disttrictId = disttrictId;
    }

    public String getDisttrictName() {
        return disttrictName;
    }

    public void setDisttrictName(String disttrictName) {
        this.disttrictName = disttrictName;
    }
}
