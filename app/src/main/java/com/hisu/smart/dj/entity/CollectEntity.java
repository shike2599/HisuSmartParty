package com.hisu.smart.dj.entity;

/**
 * Created by lichee on 2019/2/21.
 */

public class CollectEntity {

    /**
     * id : 140
     * createTime : 2019-02-21 15:42:14
     * name : 国际锐评｜未来三个月，对中美都很关键
     * resId : 21
     * url : http://222.23.86.20:8688/mp4/lilunwuzhuang/02010301000064.mp4
     * mediaType : 0
     * resType : 0
     */

    private int id;
    private String createTime;
    private String name;
    private int resId;
    private String url;
    private int mediaType;
    private int resType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
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

    public int getResType() {
        return resType;
    }

    public void setResType(int resType) {
        this.resType = resType;
    }
}
