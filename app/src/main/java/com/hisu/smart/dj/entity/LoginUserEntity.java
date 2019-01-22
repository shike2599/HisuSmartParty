package com.hisu.smart.dj.entity;

import java.io.Serializable;

/**
 *
 * @author lichee
 * @date 2019/1/22
 */

public class LoginUserEntity implements Serializable {
        /**
         * inServer : http://171.8.79.251/
         * isPartyBranch : true
         * isPartyCommittee : false
         * isPartyMember : true
         * nickname : 陈磊
         * outServer : http://171.8.79.251/
         * password : e517bf64453fe03e545d6e5e2bdc83ad
         * phone : 18629603074
         * photo : img_file/2018_12_10/1d8b7556-ad22-40fc-9442-5da21602c80f.png
         * userId : 5
         * userName : 142702198904160342
         */

        private String inServer;
        private boolean isPartyBranch;
        private boolean isPartyCommittee;
        private boolean isPartyMember;
        private String nickname;
        private String outServer;
        private String password;
        private String phone;
        private String photo;
        private int userId;
        private String userName;

        public String getInServer() {
            return inServer;
        }

        public void setInServer(String inServer) {
            this.inServer = inServer;
        }

        public boolean isIsPartyBranch() {
            return isPartyBranch;
        }

        public void setIsPartyBranch(boolean isPartyBranch) {
            this.isPartyBranch = isPartyBranch;
        }

        public boolean isIsPartyCommittee() {
            return isPartyCommittee;
        }

        public void setIsPartyCommittee(boolean isPartyCommittee) {
            this.isPartyCommittee = isPartyCommittee;
        }

        public boolean isIsPartyMember() {
            return isPartyMember;
        }

        public void setIsPartyMember(boolean isPartyMember) {
            this.isPartyMember = isPartyMember;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getOutServer() {
            return outServer;
        }

        public void setOutServer(String outServer) {
            this.outServer = outServer;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
}
