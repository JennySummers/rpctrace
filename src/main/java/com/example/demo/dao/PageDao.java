package com.example.demo.dao;

import com.example.demo.entity.Page;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface PageDao extends MongoRepository<Page,ObjectId> {
    @Override
    List<Page> findAll();
    List<Page> findByClientIdIs(String clientId);
    Page findFirstByClientIdIsAndPageUrlIs(String clientId,String pageUrl);
}
