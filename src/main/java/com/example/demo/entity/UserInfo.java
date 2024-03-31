package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Document(collection = "UserInfo")
public class UserInfo implements Serializable {
    @Id
    private ObjectId id;
    @JsonProperty("appKey")
    private String clientId;
    private int userId;
    private Map userInfo = new HashMap();
    @DateTimeFormat(pattern = "yy-MM-dd HH-mm-ss E")
    private Date time;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Map getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Map userInfo) {
        this.userInfo = userInfo;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
