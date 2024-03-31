package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Page")
public class Page {
    @Id
    private ObjectId eventId;
    private String pageUrl;
    @JsonProperty("appKey")
    private String clientId;

    public Page(String pageUrl, String clientId) {
        this.pageUrl = pageUrl;
        this.clientId = clientId;
    }

    public ObjectId getEventId() {

        return eventId;
    }

    public void setEventId(ObjectId eventId) {
        this.eventId = eventId;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
