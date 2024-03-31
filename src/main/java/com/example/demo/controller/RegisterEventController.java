package com.example.demo.controller;

import com.example.demo.entity.LoginEvent;
import com.example.demo.entity.RegisterEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin
@Controller
public class RegisterEventController {
    @Autowired
    MongoTemplate mongoTemplate;

    @RequestMapping("/registerEvent")
    @ResponseBody
    public String loginEvent(@RequestBody RegisterEvent registerEvent){
        mongoTemplate.save(registerEvent);
        return "";
    }
}
