package com.example.demo.controller;

import com.example.demo.dao.PageDao;
import com.example.demo.entity.Page;
import com.example.demo.entity.PageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin
@RestController
public class PVController {
    private static final Logger logger = LoggerFactory.getLogger(PVController.class);
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    PageDao pageDao;
    @PostMapping("/pageview")
    public String pageView(@RequestBody PageView pageView){
        logger.info(pageView.toString());
        mongoTemplate.save(pageView);
        Page page = pageDao.findFirstByClientIdIsAndPageUrlIs(pageView.getClientId(),pageView.getPageUrl());
        if(page == null){
            mongoTemplate.save(new Page(pageView.getPageUrl(),pageView.getClientId()));
        }
        return "";
    }
}
