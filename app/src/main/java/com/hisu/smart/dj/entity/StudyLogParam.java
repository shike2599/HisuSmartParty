package com.hisu.smart.dj.entity;

import java.io.Serializable;

/**
 * Created by lichee on 2019/2/22.
 */

public class StudyLogParam  implements Serializable{
    private Integer userId;
    private Integer logId;
    private Integer partyBranchId;
    private Integer partyMemberId;
    private Integer resType;
    private Integer resId;
    private String  resName;
    private Long duration;
    private Float studiedHours;
    private Float resTotalHours;
    private String pagePath;
    private String remark;

    public Integer getPartyMemberId() {
        return partyMemberId;
    }

    public void setPartyMemberId(Integer partyMemberId) {
        this.partyMemberId = partyMemberId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getPartyBranchId() {
        return partyBranchId;
    }

    public void setPartyBranchId(Integer partyBranchId) {
        this.partyBranchId = partyBranchId;
    }

    public Integer getResType() {
        return resType;
    }

    public void setResType(Integer resType) {
        this.resType = resType;
    }

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Float getStudiedHours() {
        return studiedHours;
    }

    public void setStudiedHours(Float studiedHours) {
        this.studiedHours = studiedHours;
    }

    public Float getResTotalHours() {
        return resTotalHours;
    }

    public void setResTotalHours(Float resTotalHours) {
        this.resTotalHours = resTotalHours;
    }

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "StudyLogParam{" +
                "userId=" + userId +
                ", logId=" + logId +
                ", partyBranchId=" + partyBranchId +
                ", partyMemberId=" + partyMemberId +
                ", resType=" + resType +
                ", resId=" + resId +
                ", resName='" + resName + '\'' +
                ", duration=" + duration +
                ", studiedHours=" + studiedHours +
                ", resTotalHours=" + resTotalHours +
                ", pagePath='" + pagePath + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
