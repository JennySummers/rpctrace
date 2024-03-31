package com.example.demo.dao;

import com.example.demo.entity.LoginEvent;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;


public interface LoginEventDao extends MongoRepository<LoginEvent,ObjectId> {
    @Override
    List<LoginEvent> findAll();
    List<LoginEvent> findByClientIdIsAndTimeBetween(String clientId,Date date1, Date date2);
    List<LoginEvent> findAllByTimeBetween(Date date1,Date date2);
    int countLoginEventByClientIdIsAndTimeBetween(String clientId,Date date1, Date date2);

}
