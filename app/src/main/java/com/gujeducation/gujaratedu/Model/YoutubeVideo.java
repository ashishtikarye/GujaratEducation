package com.gujeducation.gujaratedu.Model;

public class YoutubeVideo {


    private int postId, userId,like;
    private String title, image, pdf, youtubeLink,datetime, like_count, fullname, schoolname, role, user_image;
    private boolean isActive;



    public YoutubeVideo(int postId, int userId,int like, String title, String image, String pdf, String youtubeLink, String datetime, String like_count, String fullname, String schoolname, String role, String user_image, boolean isActive) {
        this.postId = postId;
        this.userId = userId;
        this.like = like;
        this.title = title;
        this.image = image;
        this.pdf = pdf;
        this.youtubeLink = youtubeLink;
        this.datetime = datetime;
        this.like_count = like_count;
        this.fullname = fullname;
        this.schoolname = schoolname;
        this.role = role;
        this.user_image = user_image;
        this.isActive = isActive;

    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
