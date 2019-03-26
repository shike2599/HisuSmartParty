package com.hisu.smart.dj.entity;

import java.io.Serializable;
import java.util.List;

public class StudyListEntity implements Serializable {

    /**
     * dataList : [{"partyMemberPhoto":"img_file/2018_12_10/1d8b7556-ad22-40fc-9442-5da21602c80f.png","partyMemberId":8,"isNeedSign":false,"publishTime":"2019-03-01 15:14:22","mediaType":2,"content":"宁乡水灾只是暂时是啥意思这是","id":222,"partyMemberName":"陈磊","name":"环姐测试数据3","images":[]},{"partyMemberPhoto":"img_file/2018_12_10/1d8b7556-ad22-40fc-9442-5da21602c80f.png","partyMemberId":8,"isNeedSign":false,"publishTime":"2019-03-01 15:07:28","mediaType":2,"id":219,"partyMemberName":"陈磊","name":"环姐测试数据2","images":[]},{"partyMemberPhoto":"img_file/2018_12_10/1d8b7556-ad22-40fc-9442-5da21602c80f.png","partyMemberId":8,"isNeedSign":false,"publishTime":"2019-03-01 15:05:11","mediaType":2,"id":218,"partyMemberName":"陈磊","name":"环姐测试数据","images":[]},{"partyMemberPhoto":"img_file/2018_12_10/1d8b7556-ad22-40fc-9442-5da21602c80f.png","partyMemberId":8,"isNeedSign":false,"publishTime":"2019-02-28 13:52:08","mediaType":2,"id":213,"partyMemberName":"陈磊","name":"心得","images":[]},{"partyMemberPhoto":"img_file/2018_12_10/1d8b7556-ad22-40fc-9442-5da21602c80f.png","partyMemberId":8,"isNeedSign":false,"publishTime":"2019-02-28 10:04:51","mediaType":2,"id":211,"partyMemberName":"陈磊","name":"看看后台有没有数据","images":[]},{"partyMemberPhoto":"img_file/2018_12_10/1d8b7556-ad22-40fc-9442-5da21602c80f.png","partyMemberId":8,"isNeedSign":false,"publishTime":"2019-02-28 10:00:47","mediaType":2,"id":210,"partyMemberName":"陈磊","name":"党员心得1","images":[]},{"partyMemberPhoto":"img_file/2018_12_10/1d8b7556-ad22-40fc-9442-5da21602c80f.png","partyMemberId":8,"isNeedSign":false,"publishTime":"2019-02-27 17:37:08","mediaType":2,"id":165,"partyMemberName":"陈磊","name":"党员心得","images":[]},{"partyMemberPhoto":"img_file/2018_12_10/1d8b7556-ad22-40fc-9442-5da21602c80f.png","partyMemberId":8,"isNeedSign":false,"publishTime":"2019-02-27 15:34:35","mediaType":2,"id":164,"partyMemberName":"陈磊","name":"Dior","images":[]},{"partyMemberPhoto":"img_file/2018_12_10/1d8b7556-ad22-40fc-9442-5da21602c80f.png","partyMemberId":8,"isNeedSign":false,"publishTime":"2019-02-27 15:20:43","mediaType":2,"id":163,"partyMemberName":"陈磊","name":"学习心得测试","images":[]},{"partyMemberPhoto":"img_file/2018_12_10/1d8b7556-ad22-40fc-9442-5da21602c80f.png","partyMemberId":8,"isNeedSign":true,"publishTime":"2018-11-08 12:00:00","mediaType":2,"content":"测试内容是否正常上传","id":221,"partyMemberName":"陈磊","name":"测试环20190301","images":[]},{"partyMemberPhoto":"img_file/2018_12_10/1d8b7556-ad22-40fc-9442-5da21602c80f.png","partyMemberId":8,"isNeedSign":true,"publishTime":"2018-11-08 12:00:00","mediaType":2,"id":220,"partyMemberName":"陈磊","name":"测试环20190301","images":[]}]
     * systemTime : 2019-03-01 15:17:02
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
         * partyMemberPhoto : img_file/2018_12_10/1d8b7556-ad22-40fc-9442-5da21602c80f.png
         * partyMemberId : 8
         * isNeedSign : false
         * publishTime : 2019-03-01 15:14:22
         * mediaType : 2
         * content : 宁乡水灾只是暂时是啥意思这是
         * id : 222
         * partyMemberName : 陈磊
         * name : 环姐测试数据3
         * images : []
         */

        private String partyMemberPhoto;
        private int partyMemberId;
        private boolean isNeedSign;
        private String publishTime;
        private int mediaType;
        private String content;
        private int id;
        private String partyMemberName;
        private String name;
        private List<String> images;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        private String url;
        public String getPartyMemberPhoto() {
            return partyMemberPhoto;
        }

        public void setPartyMemberPhoto(String partyMemberPhoto) {
            this.partyMemberPhoto = partyMemberPhoto;
        }

        public int getPartyMemberId() {
            return partyMemberId;
        }

        public void setPartyMemberId(int partyMemberId) {
            this.partyMemberId = partyMemberId;
        }

        public boolean isIsNeedSign() {
            return isNeedSign;
        }

        public void setIsNeedSign(boolean isNeedSign) {
            this.isNeedSign = isNeedSign;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public int getMediaType() {
            return mediaType;
        }

        public void setMediaType(int mediaType) {
            this.mediaType = mediaType;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPartyMemberName() {
            return partyMemberName;
        }

        public void setPartyMemberName(String partyMemberName) {
            this.partyMemberName = partyMemberName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
