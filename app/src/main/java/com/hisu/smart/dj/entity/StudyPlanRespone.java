package com.hisu.smart.dj.entity;

import java.io.Serializable;

public class StudyPlanRespone implements Serializable {

    /**
     * systemTime : 2019-01-29 12:25:46
     * resultCode : 200
     * data : {"topicPlan":{"totalHours":9.54,"planTotalHours":26},"commPlan":{"totalHours":50.73,"planTotalHours":120}}
     * resultDesc : 操作成功
     */

    private String systemTime;
    private int resultCode;
    private DataBean data;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public static class DataBean {
        /**
         * topicPlan : {"totalHours":9.54,"planTotalHours":26}
         * commPlan : {"totalHours":50.73,"planTotalHours":120}
         */

        private TopicPlanBean topicPlan;
        private CommPlanBean commPlan;

        public TopicPlanBean getTopicPlan() {
            return topicPlan;
        }

        public void setTopicPlan(TopicPlanBean topicPlan) {
            this.topicPlan = topicPlan;
        }

        public CommPlanBean getCommPlan() {
            return commPlan;
        }

        public void setCommPlan(CommPlanBean commPlan) {
            this.commPlan = commPlan;
        }

        public static class TopicPlanBean {
            /**
             * totalHours : 9.54
             * planTotalHours : 26
             */

            private double totalHours;
            private int planTotalHours;

            public double getTotalHours() {
                return totalHours;
            }

            public void setTotalHours(double totalHours) {
                this.totalHours = totalHours;
            }

            public int getPlanTotalHours() {
                return planTotalHours;
            }

            public void setPlanTotalHours(int planTotalHours) {
                this.planTotalHours = planTotalHours;
            }
        }

        public static class CommPlanBean {
            /**
             * totalHours : 50.73
             * planTotalHours : 120
             */

            private double totalHours;
            private int planTotalHours;

            public double getTotalHours() {
                return totalHours;
            }

            public void setTotalHours(double totalHours) {
                this.totalHours = totalHours;
            }

            public int getPlanTotalHours() {
                return planTotalHours;
            }

            public void setPlanTotalHours(int planTotalHours) {
                this.planTotalHours = planTotalHours;
            }
        }
    }
}
