package com.hisu.smart.dj.entity;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author lichee
 * @date 2019/1/22
 */

public class InformationResponse<T> implements Serializable {

    /**
     * dataList : [{"id":13,"icon":"","publishTime":"2018-11-26 00:00:00","name":"蓬佩奥：沙特王储和卡舒吉之死无直接联系","url":"","mediaType":1},{"id":10,"icon":"","publishTime":"2018-11-24 00:00:00","name":"海关总署允许进口日本新潟县大米 此前曾禁止进口","url":"","mediaType":1},{"id":9,"icon":"","publishTime":"2018-11-23 00:00:00","name":"基因峰会发布声明:贺建奎项目存缺陷 不应临床试验","url":"","mediaType":1},{"id":8,"icon":"img_file/2018_11_29/d8ffbe00-9361-4f69-857b-c0f814cb86f7.jpeg","publishTime":"2018-11-15 00:00:00","name":"波音回应狮航空难报告:客机安全 操作及维修不当","url":"","mediaType":1}]
     * totalNum : 4
     * systemTime : 2019-01-22 03:39:14
     * currPage : 1
     * resultCode : 200
     * pageSize : 30
     * resultDesc : 操作成功
     * totalPage : 1
     * inServer : http://171.8.79.251/
     * outServer : http://171.8.79.251/
     */

    private int totalNum;
    private String systemTime;
    private int currPage;
    private int resultCode;
    private int pageSize;
    private String resultDesc;
    private int totalPage;
    private String inServer;
    private String outServer;
    private List<T> dataList;

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public String getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(String systemTime) {
        this.systemTime = systemTime;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getInServer() {
        return inServer;
    }

    public void setInServer(String inServer) {
        this.inServer = inServer;
    }

    public String getOutServer() {
        return outServer;
    }

    public void setOutServer(String outServer) {
        this.outServer = outServer;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
