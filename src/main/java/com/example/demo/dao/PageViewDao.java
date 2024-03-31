package com.example.demo.dao;

import com.example.demo.entity.PageView;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.swing.*;
import java.util.Date;
import java.util.List;


public interface PageViewDao extends MongoRepository<PageView,ObjectId> {
    @Override
    List<PageView> findAll();
    PageView findFirstByTimeBeforeAndUserIdIsAndEndTimeIsNullAndClientIdIsAndPageUrlIs(Date date,int userId,String clientId,String url);
    List<PageView> findByClientIdIsAndTimeBetween(String clientId,Date date1,Date date2);//web传参
    List<PageView> findByPageUrlIsAndClientIdIsAndTimeBetween(String pageUrl,String clientId,Date date1,Date date2);//pageUrl=pageName[i]
    int countPageViewByClientIdIsAndPageUrlIsAndTimeBetween(String clientId,String pageUrl,Date data1, Date data2);
    List<PageView> findByClientIdIsAndTimeBetweenAndPageUrlIs(String clientId,Date date1,Date date2,String page);//web传参
    List<PageView> findAllByClientId(String clientID);

}
