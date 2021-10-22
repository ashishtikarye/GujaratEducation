package com.gujeducation.gujaratedu.Model;

public class Contacts {
    String name;
    String phone;
    private boolean isSelected;

    public Contacts(String name, String phone, boolean isSelected) {
        this.name = name;
        this.phone = phone;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}
