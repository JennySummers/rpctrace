package com.example.demo.controller;

import com.example.demo.entity.Component;
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
public class ComponentController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PVController.class);

    @Autowired
    MongoTemplate mongoTemplate;

    @RequestMapping("/component")
    @ResponseBody
    public String component(@RequestBody Component component){
        logger.info(component.toString());
        mongoTemplate.save(component);
        return "";
    }
}
