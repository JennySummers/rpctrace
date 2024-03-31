package com.example.demo.dao;

import com.example.demo.entity.RegisterEvent;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;


public interface RegisterEventDao extends MongoRepository<RegisterEvent,ObjectId> {
    @Override
    List<RegisterEvent> findAll();
    int countRegisterEventByClientIdIsAndTimeBetween(String clientId,Date date1, Date date2);
    //一段时间内新增用户集合，通过此集合获取userID
    List<RegisterEvent> findByClientIdIsAndTimeBetween(String clientId,Date date1, Date date2);
    List<RegisterEvent> findAllByTimeBetween(Date date1,Date date2);
}
