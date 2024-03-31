package com.example.demo.controller;

import com.example.demo.dao.PageViewDao;
import com.example.demo.entity.LeaveEvent;
import com.example.demo.entity.PageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin
@Controller
public class LeaveController {

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    PageViewDao pageViewDao;

    @PostMapping("/leave")
    @ResponseBody
    public String leave(@RequestBody LeaveEvent leaveEvent){
        System.out.println(leaveEvent.toString());
        PageView pageView = pageViewDao.findFirstByTimeBeforeAndUserIdIsAndEndTimeIsNullAndClientIdIsAndPageUrlIs(leaveEvent.getTime(),leaveEvent.getUserId(),leaveEvent.getClientId(),leaveEvent.getUrl());
        if(pageView != null){
            pageView.setEndTime(leaveEvent.getTime());
            pageViewDao.save(pageView);
        }
        return "";
    }
}
