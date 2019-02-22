package com.hisu.smart.dj.entity;

import java.io.Serializable;

/**
 * Created by lichee on 2019/2/22.
 */

public class StudiedDetailResponse implements Serializable{

    /**
     * systemTime : 2019-02-22 09:10:31
     * resultCode : 200
     * data : {"id":49,"duration":0,"hours":0.03,"partyBranchId":14,"available":true,"totalHours":30}
     * resultDesc : 操作成功
     */

    private String systemTime;
    private int resultCode;
    private StudiedDetailEntity data;
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

    public StudiedDetailEntity getData() {
        return data;
    }

    public void setData(StudiedDetailEntity data) {
        this.data = data;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }
}
