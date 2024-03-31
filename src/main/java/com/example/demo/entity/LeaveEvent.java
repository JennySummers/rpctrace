package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Document(collection = "Leave")
public class LeaveEvent {
    @Id
    private ObjectId id;
    private int userId;
    @JsonProperty("pageUrl")
    private String url;
    @JsonProperty("appKey")
    private String clientId;
    @DateTimeFormat(pattern = "yy-MM-dd HH-mm-ss E")
    private Date time;

    @Override
    public String toString() {
        return "LeaveEvent{" +
                "id=" + id +
                ", userId=" + userId +
                ", url='" + url + '\'' +
                ", clientId='" + clientId + '\'' +
                ", time=" + time +
                '}';
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public LeaveEvent() {

    }
}
