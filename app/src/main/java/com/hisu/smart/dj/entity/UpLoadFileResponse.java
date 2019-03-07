package com.hisu.smart.dj.entity;

import java.io.Serializable;
import java.util.List;

public class UpLoadFileResponse implements Serializable {

    /**
     * dataList : [{"inputTagName":"file1","url":"http://171.8.79.251/temp_file/e7e5f79e-9d51-480a-9940-df6f36e5db16.png","path":"temp_file/e7e5f79e-9d51-480a-9940-df6f36e5db16.png"},{"inputTagName":"file2","url":"http://171.8.79.251/temp_file/1d7bb139-2a10-4391-b42c-5a4f8c9ab940.png","path":"temp_file/1d7bb139-2a10-4391-b42c-5a4f8c9ab940.png"}]
     * resultCode : 200
     * resultDesc : 操作成功
     * systemTime : 2019-03-06 10:49:11
     */

    private int resultCode;
    private String resultDesc;
    private String systemTime;
    private List<FileListBean> dataList;

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

    public String getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(String systemTime) {
        this.systemTime = systemTime;
    }

    public List<FileListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<FileListBean> dataList) {
        this.dataList = dataList;
    }

    public static class FileListBean {
        /**
         * inputTagName : file1
         * url : http://171.8.79.251/temp_file/e7e5f79e-9d51-480a-9940-df6f36e5db16.png
         * path : temp_file/e7e5f79e-9d51-480a-9940-df6f36e5db16.png
         */

        private String inputTagName;
        private String url;
        private String path;

        public String getInputTagName() {
            return inputTagName;
        }

        public void setInputTagName(String inputTagName) {
            this.inputTagName = inputTagName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
