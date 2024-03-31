package com.example.demo.dao;

import com.example.demo.entity.CustomEventName;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface CustomEventNameDao extends MongoRepository<CustomEventName,ObjectId> {
    CustomEventName findByCustomEventNameIsAndClientIdIs(String customEventName,String clientId);
    List<CustomEventName> findAllByClientIdIs(String clientId);
}
