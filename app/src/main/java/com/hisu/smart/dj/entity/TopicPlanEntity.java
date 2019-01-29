package com.hisu.smart.dj.entity;

import java.io.Serializable;

/**
 *
 * @author lichee
 * @date 2019/1/29
 */

public class TopicPlanEntity implements Serializable{

    /**
     * id : 8
     * icon : img_file/2019_01_18/2471af99-ea18-4647-8c49-aaedf110329f.jpg
     * publishTime : 2018-12-24 00:00:00
     * name : 历史性成就与历史性变革1
     * totalHours : 1
     * url : http://222.23.86.20:8688/mp4/shijiuda/01011001000001.mp4
     * mediaType : 0
     */

    private int id;
    private String icon;
    private String publishTime;
    private String name;
    private int totalHours;
    private String url;
    private int mediaType;

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

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
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
}
