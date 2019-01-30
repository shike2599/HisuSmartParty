package com.hisu.smart.dj.entity;

import java.io.Serializable;

public class MemberInfoResponse implements Serializable{

    /**
     * systemTime : 2019-01-29 11:19:12
     * resultCode : 200
     * data : {"id":8,"sex":1,"phone":"18629603074","idCard":"142702198904160342","status":0,"name":"陈磊","partyBranchId":14,"code":"061201120","integral":0}
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
         * id : 8
         * sex : 1
         * phone : 18629603074
         * idCard : 142702198904160342
         * status : 0
         * name : 陈磊
         * partyBranchId : 14
         * code : 061201120
         * integral : 0
         */

        private int id;
        private int sex;
        private String phone;
        private String idCard;
        private int status;
        private String name;
        private int partyBranchId;
        private String code;
        private int integral;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPartyBranchId() {
            return partyBranchId;
        }

        public void setPartyBranchId(int partyBranchId) {
            this.partyBranchId = partyBranchId;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }
    }
}
