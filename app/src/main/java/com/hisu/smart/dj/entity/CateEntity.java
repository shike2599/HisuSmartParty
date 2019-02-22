package com.hisu.smart.dj.entity;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author lichee
 * @date 2019/1/30
 */

public class CateEntity implements Serializable {

    /**
     * id : 2
     * name : 党史研究
     * icons : []
     * code : 30031
     * mediaType : 1
     */

    private int id;
    private String name;
    private String code;
    private int mediaType;
    private List<?> icons;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getMediaType() {
        return mediaType;
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    public List<?> getIcons() {
        return icons;
    }

    public void setIcons(List<?> icons) {
        this.icons = icons;
    }

    @Override
    public String toString() {
        return "CommonCate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", mediaType=" + mediaType +
                ", icons=" + icons +
                '}';
    }
}
