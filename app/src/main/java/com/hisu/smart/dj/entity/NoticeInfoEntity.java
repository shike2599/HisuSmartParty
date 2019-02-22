package com.hisu.smart.dj.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 消息通知数据
 */
public class NoticeInfoEntity implements Serializable {

    /**
     * dataList : [{"id":5,"content":"海数科技智慧党建2.0平台上线了，欢迎大家使用！","publishTime":"2019-02-12 17:12:00","name":"公告","hasRead":false},{"id":4,"content":"　　近日，中央组织部党员教育中心主办的第四届全国党员教育培训教材展示交流活动圆满结束。\r\n\r\n　　第四届全国党员教育培训教材展示交流活动，是深入学习贯彻习近平新时代中国特色社会主义思想和党的十九大精神，贯彻落实全国党员教育培训工作规划的具体举措，集中展示了两年来各地区各部门各单位党员教育培训教材建设的最新成果，推出了一批高质量教材，发现了一批优秀作者，对进一步提高党员教育培训教材建设水平起到了指导引领作用。\r\n\r\n　　各省（区、市）和新疆生产建设兵团、39个中央和国家机关、17个中央企业、5个中管高校及中央军委政治工作部等96家单位，推荐了544种参评教材。主办方严格按照评审标准和程序，紧密结合基层党员实际需求，组织了专家评审、基层评价等，最终评选出74种获奖教材，其中精品教材13种，创新教材23种，优秀教材38种。获奖教材将上载共产党员网（http://www.12371.cn）和中国国家数字图书馆（http://www.nlc.cn），供广大党员学习使用。","publishTime":"2019-02-12 16:51:28","name":"第四届全国党员教育培训教材展示交流活动成功举办","hasRead":false}]
     * systemTime : 2019-02-20 11:25:17
     * resultCode : 200
     * resultDesc : 操作成功
     */

    private String systemTime;
    private int resultCode;
    private String resultDesc;
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

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean implements Serializable {
        /**
         * id : 5
         * content : 海数科技智慧党建2.0平台上线了，欢迎大家使用！
         * publishTime : 2019-02-12 17:12:00
         * name : 公告
         * hasRead : false
         */

        private int id;
        private String content;
        private String publishTime;
        private String name;
        private boolean hasRead;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isHasRead() {
            return hasRead;
        }

        public void setHasRead(boolean hasRead) {
            this.hasRead = hasRead;
        }
    }
}
