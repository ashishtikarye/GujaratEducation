package com.gujeducation.gujaratedu.Model;

/**
 * Created by Tikarye Ashish on 17/09/2019.
 */

public class Links {
    String mediumId;
    String title;
    String page_link;
    String status;
    String is_delete;

    public Links(String mediumId, String title, String page_link, String status, String is_delete) {
        this.mediumId = mediumId;
        this.title = title;
        this.page_link = page_link;
        this.status = status;
        this.is_delete = is_delete;
    }

    public String getMediumId() {
        return mediumId;
    }

    public void setMediumId(String mediumId) {
        this.mediumId = mediumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPage_link() {
        return page_link;
    }

    public void setPage_link(String page_link) {
        this.page_link = page_link;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
    }
}
