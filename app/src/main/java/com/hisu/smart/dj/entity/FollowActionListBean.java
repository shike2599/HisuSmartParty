package com.hisu.smart.dj.entity;

import java.io.Serializable;
import java.util.List;

public class FollowActionListBean implements Serializable {

    /**
     * dataList : [{"id":1,"name":"本职工作","icons":[],"code":"5001","mediaType":1},{"id":2,"name":"党组工作","icons":[],"code":"5002","mediaType":1},{"id":3,"name":"典型事迹","icons":[],"code":"5003","mediaType":1},{"id":4,"name":"脱贫攻坚","icons":[],"code":"5004","mediaType":1},{"id":5,"name":"志愿者活动","icons":[],"code":"5005","mediaType":1},{"id":6,"name":"困难帮助","icons":[],"code":"5006","mediaType":1}]
     * systemTime : 2019-02-01 06:26:09
     * resultCode : 200
     * resultDesc : 操作成功
     * inServer : http://171.8.79.251/
     * outServer : http://171.8.79.251/
     */

    private String systemTime;
    private int resultCode;
    private String resultDesc;
    private String inServer;
    private String outServer;
    private List<DataListBean> dataList;

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

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean {
        /**
         * id : 1
         * name : 本职工作
         * icons : []
         * code : 5001
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
    }
}
