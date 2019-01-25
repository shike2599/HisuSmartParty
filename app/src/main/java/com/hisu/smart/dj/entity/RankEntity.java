package com.hisu.smart.dj.entity;

import java.io.Serializable;

/**
 *
 * @author lichee
 * @date 2019/1/25
 */

public class RankEntity implements Serializable {
        /**
         * id : 8
         * commHours : 15.11
         * name : 陈磊
         * topicHours : 0
         * totalHours : 15.11
         */

        private int id;
        private double commHours;
        private String name;
        private int topicHours;
        private double totalHours;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getCommHours() {
            return commHours;
        }

        public void setCommHours(double commHours) {
            this.commHours = commHours;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getTopicHours() {
            return topicHours;
        }

        public void setTopicHours(int topicHours) {
            this.topicHours = topicHours;
        }

        public double getTotalHours() {
            return totalHours;
        }

        public void setTotalHours(double totalHours) {
            this.totalHours = totalHours;
        }

    @Override
    public String toString() {
        return "RankEntity{" +
                "id=" + id +
                ", commHours=" + commHours +
                ", name='" + name + '\'' +
                ", topicHours=" + topicHours +
                ", totalHours=" + totalHours +
                '}';
    }
}
