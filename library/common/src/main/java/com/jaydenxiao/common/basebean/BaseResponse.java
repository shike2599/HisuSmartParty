package com.jaydenxiao.common.basebean;

import java.io.Serializable;

/**
 * des:封装服务器返回数据
 * on 2016.09.9:47
 * @author lichee
 */
public class BaseResponse<T> implements Serializable {
    public String resultCode;
    public String resultDesc;
    public String systemTime;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T data;

    public boolean success() {
        return "200".equals(resultCode);
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "resultCode='" + resultCode + '\'' +
                ", resultDesc='" + resultDesc + '\'' +
                ", systemTime='" + systemTime + '\'' +
                ", data=" + data +
                '}';
    }
}
