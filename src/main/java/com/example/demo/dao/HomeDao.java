package com.example.demo.dao;

import com.example.demo.entity.PageView;
import org.springframework.data.mongodb.core.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import com.example.demo.entity.UserInfo;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Field;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public interface HomeDao extends MongoRepository<PageView,Integer> {
    List<PageView> findPageViewByClientId(String Appkey);
}
