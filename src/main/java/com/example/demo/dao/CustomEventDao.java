package com.example.demo.dao;

import com.example.demo.entity.CustomEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;



public interface CustomEventDao extends MongoRepository<CustomEvent,Integer> {
    @Override
    List<CustomEvent> findAll();
    //List<CustomEvent> findByTimeBetween(Date date1,Date date2);
    CustomEvent findFirstByClientIdIsAndCustomEventNameIs(String client,String eventName);
    List<CustomEvent> findByClientIdIsAndTimeBetweenAndCustomEventNameIs(String client,Date date1,Date date2,String eventName);
    //CustomEvent findByClientIdIsAndCustomEventNameIs(String client,String name);

}
