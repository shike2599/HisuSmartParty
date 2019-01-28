package com.jaydenxiao.common.basebean;

import java.io.Serializable;
import java.util.List;

/**
 * des:封装服务器返回数据
 * on 2016.09.9:47
 * @author lichee
 */
public class BaseResponse<T> implements Serializable {
    private String resultCode;
    private String resultDesc;
    private String systemTime;
    private List<T> dataList;

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


    public boolean success() {
        return true;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "resultCode='" + resultCode + '\'' +
                ", resultDesc='" + resultDesc + '\'' +
                ", systemTime='" + systemTime + '\'' +
                ", dataList=" + dataList +
                '}';
    }
}
