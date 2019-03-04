package com.hisu.smart.dj.entity;

import java.io.Serializable;

/**
 *
 * @author lichee
 * @date 2019/1/22
 */

public class InformationEntity implements Serializable {
    /**
     * id : 13
     * icon :
     * publishTime : 2018-11-26 00:00:00
     * name : 蓬佩奥：沙特王储和卡舒吉之死无直接联系
     * url :
     * mediaType : 1
     */

    private int id;
    private String icon;
    private String publishTime;
    private String name;
    private String url;
    private int mediaType;
    private int watchNum;

    public int getWatchNum() {
        return watchNum;
    }

    public void setWatchNum(int watchNum) {
        this.watchNum = watchNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMediaType() {
        return mediaType;
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    @Override
    public String toString() {
        return "InformationEntity{" +
                "id=" + id +
                ", icon='" + icon + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", mediaType=" + mediaType +
                '}';
    }
}