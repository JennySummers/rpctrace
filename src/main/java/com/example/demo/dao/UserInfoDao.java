package com.example.demo.dao;

import com.example.demo.entity.UserInfo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface UserInfoDao extends MongoRepository<UserInfo,ObjectId> {
    @Override
    List<UserInfo> findAll();
    UserInfo findByClientIdIsAndUserIdIs(String clientId,int userId);
    List<UserInfo> findByClientIdIs(String clientId);
    UserInfo findFirstByClientIdIs(String clientId);
    List<UserInfo> findByClientIdIsAndTimeBetween(String clientId, Date date1, Date date2);
    List<UserInfo> findByUserIdInAndClientIdIs(int[] userId,String clientId);
    List<UserInfo> findByUserIdInAndClientIdIs(Collection userId, String clientId);
}
