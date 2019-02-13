package com.hisu.smart.dj.entity;

import java.io.Serializable;

public class NotingResponse implements Serializable{


    /**
     * systemTime : 2019-02-13 09:01:40
     * resultCode : 200
     * resultDesc : 操作成功
     */

    private String systemTime;
    private int resultCode;
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

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }
}
