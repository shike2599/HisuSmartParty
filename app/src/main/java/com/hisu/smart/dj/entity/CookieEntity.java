package com.hisu.smart.dj.entity;

import java.io.Serializable;

public class CookieEntity implements Serializable {
    private String cookieKey;
    private String cookieValue;

    public String getCookieKey() {
        return cookieKey;
    }

    public void setCookieKey(String cookieKey) {
        this.cookieKey = cookieKey;
    }

    public String getCookieValue() {
        return cookieValue;
    }

    public void setCookieValue(String cookieValue) {
        this.cookieValue = cookieValue;
    }
}
