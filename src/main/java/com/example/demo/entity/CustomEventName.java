package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "CustomEventName")
public class CustomEventName {
    @Id
    private ObjectId eventId;
    private String customEventName;
    @JsonProperty("appKey")
    private String clientId;

    public CustomEventName() {
    }

    public CustomEventName(String customEventName, String clientId) {

        this.customEventName = customEventName;
        this.clientId = clientId;
    }

    public ObjectId getEventId() {
        return eventId;
    }

    public void setEventId(ObjectId eventId) {
        this.eventId = eventId;
    }

    public String getCustomEventName() {
        return customEventName;
    }

    public void setCustomEventName(String customEventName) {
        this.customEventName = customEventName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
