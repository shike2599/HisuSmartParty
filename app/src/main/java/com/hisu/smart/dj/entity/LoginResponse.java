package com.hisu.smart.dj.entity;

/**
 * Created by lichee on 2019/1/29.
 */

public class LoginResponse {

    /**
     * data : {"inServer":"http://171.8.79.251/","isPartyBranch":true,"isPartyCommittee":false,"isPartyMember":true,"nickname":"陈磊","outServer":"http://171.8.79.251/","password":"e517bf64453fe03e545d6e5e2bdc83ad","phone":"18629603074","photo":"img_file/2018_12_10/1d8b7556-ad22-40fc-9442-5da21602c80f.png","userId":5,"userName":"142702198904160342"}
     * resultCode : 200
     * resultDesc : 操作成功
     * systemTime : 2019-01-29 06:06:29
     */

    private LoginUserEntity data;
    private String resultCode;
    private String resultDesc;
    private String systemTime;

    public LoginUserEntity getData() {
        return data;
    }

    public void setData(LoginUserEntity data) {
        this.data = data;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public String getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(String systemTime) {
        this.systemTime = systemTime;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "data=" + data +
                ", resultCode='" + resultCode + '\'' +
                ", resultDesc='" + resultDesc + '\'' +
                ", systemTime='" + systemTime + '\'' +
                '}';
    }
}
