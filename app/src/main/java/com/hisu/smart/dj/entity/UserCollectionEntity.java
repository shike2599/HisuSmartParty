package com.hisu.smart.dj.entity;

import java.io.Serializable;

public class UserCollectionEntity implements Serializable {

    /**
     * systemTime : 2019-02-18 07:28:41
     * resultCode : 200
     * data : 0
     * resultDesc : 操作成功
     */

    private String systemTime;
    private int resultCode;
    private int data;
    private String resultDesc;

    public String getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(String systemTime) {
        this.systemTime = systemTime;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }
}
