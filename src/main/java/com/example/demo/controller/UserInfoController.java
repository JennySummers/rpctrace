package com.example.demo.controller;

import com.example.demo.dao.UserInfoDao;
import com.example.demo.entity.UserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin
@Controller
public class UserInfoController {
    private static final Logger logger = LoggerFactory.getLogger(CustomEventController.class);

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    UserInfoDao userInfoDao;

    @ResponseBody
    @PostMapping("/userInfo")
    public String userInfo(@RequestBody UserInfo userInfo){
        logger.info(userInfo.toString());
        UserInfo userInfo1 = userInfoDao.findByClientIdIsAndUserIdIs(userInfo.getClientId(),userInfo.getUserId());
        if(userInfo1 != null){
            Map map1 = userInfo1.getUserInfo();
            Map map = userInfo.getUserInfo();
            Set set = map.keySet();
            for (Object aSet : set) {
                map1.put(aSet,map.get(aSet));
            }
            userInfo1.setUserInfo(map1);
            mongoTemplate.save(userInfo1);
        }else{
            mongoTemplate.save(userInfo);
        }
        return "";
    }
}
