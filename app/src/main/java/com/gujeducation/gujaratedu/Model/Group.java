package com.gujeducation.gujaratedu.Model;

public class Group {
    int groupId,userId;
    String group_name,particepentsId;

    public Group(int groupId, int userId, String group_name, String particepentsId) {
        this.groupId = groupId;
        this.userId = userId;
        this.group_name = group_name;
        this.particepentsId = particepentsId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getParticepentsId() {
        return particepentsId;
    }

    public void setParticepentsId(String particepentsId) {
        this.particepentsId = particepentsId;
    }
}
