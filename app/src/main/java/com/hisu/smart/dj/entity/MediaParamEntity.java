package com.hisu.smart.dj.entity;

import java.io.Serializable;

/**
 * Created by lichee on 2019/2/22.
 */

public class MediaParamEntity implements Serializable {
    private Integer userId;
    private Integer resId;
    private Integer resType;
    private String title;
    private String url;
    private String cover;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public Integer getResType() {
        return resType;
    }

    public void setResType(Integer resType) {
        this.resType = resType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "MediaParamEntity{" +
                "userId=" + userId +
                ", resId=" + resId +
                ", resType=" + resType +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }
}
