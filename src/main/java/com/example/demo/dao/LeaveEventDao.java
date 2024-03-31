package com.example.demo.dao;

import com.example.demo.entity.LeaveEvent;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;



public interface LeaveEventDao extends MongoRepository<LeaveEvent,ObjectId> {
    @Override
    List<LeaveEvent> findAll();
}
