package com.hisu.smart.dj.entity;

/**
 *
 * @author lichee
 * @date 2019/1/22
 */

public class GridViewItemEntity {
    private Integer icon;
    private String title;
    private String desc;

    public GridViewItemEntity(Integer icon, String title, String desc) {
        this.icon = icon;
        this.title = title;
        this.desc = desc;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
