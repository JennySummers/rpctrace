package com.example.demo.controller;

import com.example.demo.entity.ComponentId;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin
@Controller
public class ComponentIdController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PVController.class);

    @Autowired
    MongoTemplate mongoTemplate;

    @RequestMapping("/componentId")
    @ResponseBody
    public String componentId(@RequestBody ComponentId componentId){
        logger.info(componentId.toString());
        mongoTemplate.save(componentId);
        return "";
    }
}
