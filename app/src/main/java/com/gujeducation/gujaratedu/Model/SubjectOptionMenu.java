package com.gujeducation.gujaratedu.Model;

public class SubjectOptionMenu {
    private int menuId;
    private String menuName;
    private String pdfName;

    public SubjectOptionMenu(int menuId, String menuName, String pdfName) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.pdfName = pdfName;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getPdfName() {
        return pdfName;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }
}
