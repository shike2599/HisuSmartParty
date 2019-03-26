package com.hisu.smart.dj.entity;

import java.io.Serializable;

/**
 * Created by lichee on 2019/3/25.
 */

public class ThumbsUpEntity implements Serializable {
    private int id;
    private int userId;
    private int partyMemberId;
    private String partyMemberName;
    private String partyMemberPhoto;
    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPartyMemberId() {
        return partyMemberId;
    }

    public void setPartyMemberId(int partyMemberId) {
        this.partyMemberId = partyMemberId;
    }

    public String getPartyMemberName() {
        return partyMemberName;
    }

    public void setPartyMemberName(String partyMemberName) {
        this.partyMemberName = partyMemberName;
    }

    public String getPartyMemberPhoto() {
        return partyMemberPhoto;
    }

    public void setPartyMemberPhoto(String partyMemberPhoto) {
        this.partyMemberPhoto = partyMemberPhoto;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ThumbsUpEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", partyMemberId=" + partyMemberId +
                ", partyMemberName='" + partyMemberName + '\'' +
                ", partyMemberPhoto='" + partyMemberPhoto + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
