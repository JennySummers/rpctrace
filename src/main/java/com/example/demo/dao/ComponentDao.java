package com.example.demo.dao;

import com.example.demo.entity.Component;
import com.example.demo.entity.CustomEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Date;

public interface ComponentDao  extends MongoRepository<Component,Integer> {
    @Override
    List<Component> findAll();
    List<Component> findByClientIdIsAndTimeBetweenAndPageTitleIs(String clientId,Date date1,Date date2,String pageTitle);
}
