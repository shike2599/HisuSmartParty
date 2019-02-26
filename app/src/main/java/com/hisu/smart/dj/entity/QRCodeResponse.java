package com.hisu.smart.dj.entity;

import java.io.Serializable;

public class QRCodeResponse implements Serializable {

    /**
     * stbType : 0
     * stbUserId : 1
     */

    private int stbType;
    private int stbUserId;

    public int getStbType() {
        return stbType;
    }

    public void setStbType(int stbType) {
        this.stbType = stbType;
    }

    public int getStbUserId() {
        return stbUserId;
    }

    public void setStbUserId(int stbUserId) {
        this.stbUserId = stbUserId;
    }
}
