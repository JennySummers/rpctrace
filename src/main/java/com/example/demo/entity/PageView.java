package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


@Document(collection = "PageView")
public class PageView implements Serializable {

    private static final long serialVersionUID = -60781486773939584L;

    @Id
    private ObjectId eventId;
    /**
     * 事件名
     */
    private String eventName;
    /**
     * appKey
     */
    @JsonProperty("appKey")
    private String clientId;
    /**
     * 页面标识
     */
    private String pageUrl;
    /**
     * 用户ip
     */
    private String userIp;
    /**
     * 用户id
     */
    private int userId;
    /**
     * 载入时间
     */
    @DateTimeFormat(pattern = "yy-MM-dd HH-mm-ss E")
    private Date time;
    /**
     * 离开时间
     */
    @DateTimeFormat(pattern = "yy-MM-dd HH-mm-ss E")
    private Date endTime;

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public ObjectId getEventId() {
        return eventId;
    }

    public void setEventId(ObjectId eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public PageView(String pageUrl, String userIp, int userId, Date time) {
        this.pageUrl = pageUrl;
        this.userIp = userIp;
        this.userId = userId;
        this.time = time;
    }

    public PageView() {
    }

    @Override
    public String toString() {
        return "PageView{" +
                "pageUrl='" + pageUrl + '\'' +
                ", userIp='" + userIp + '\'' +
                ", userId='" + userId + '\'' +
                ", time=" + time +
                '}';
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
