package com.example.demo.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "ClientAppKey")
public class ClientAppKey implements Serializable {

    @Id
    private ObjectId id;
    private String appKey;
    private String password;

    public ObjectId getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ClientAppKey{" +
                "id=" + id +
                ", appKey='" + appKey +'\'' +
                '}';
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public ClientAppKey() {

    }
}
