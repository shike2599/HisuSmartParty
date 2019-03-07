package com.hisu.smart.dj.entity;

import java.io.Serializable;

/**
 * Created by lichee on 2019/2/22.
 */

public class StudiedDetailEntity implements Serializable{
    /**
     * id : 49
     * duration : 0
     * hours : 0.03
     * partyBranchId : 14
     * available : true
     * totalHours : 30
     */

    private int id;
    private int duration;
    private float hours;
    private int partyBranchId;
    private int partyMemberId;
    private boolean available;
    private float totalHours;

    public int getPartyMemberId() {
        return partyMemberId;
    }

    public void setPartyMemberId(int partyMemberId) {
        this.partyMemberId = partyMemberId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public float getHours() {
        return hours;
    }

    public void setHours(float hours) {
        this.hours = hours;
    }

    public int getPartyBranchId() {
        return partyBranchId;
    }

    public void setPartyBranchId(int partyBranchId) {
        this.partyBranchId = partyBranchId;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public float getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(float totalHours) {
        this.totalHours = totalHours;
    }

    @Override
    public String toString() {
        return "StudiedDetailEntity{" +
                "id=" + id +
                ", duration=" + duration +
                ", hours=" + hours +
                ", partyBranchId=" + partyBranchId +
                ", partyMemberId=" + partyMemberId +
                ", available=" + available +
                ", totalHours=" + totalHours +
                '}';
    }
}
