package com.example.demo.dao;

import com.example.demo.entity.Component;
import com.example.demo.entity.ComponentId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;


public interface ComponentIdDao extends MongoRepository<ComponentId,ObjectId> {
    List<ComponentId> findByClientIdIsAndTimeBetweenAndPageTitleIs(String clientId, Date date1, Date date2, String pageTitle);
}
