package com.example.demo.controller;

import com.example.demo.entity.LoginEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin
@Controller
public class LoginEventController {
    @Autowired
    MongoTemplate mongoTemplate;

    @RequestMapping("/loginEvent")
    @ResponseBody
    public String loginEvent(@RequestBody LoginEvent loginEvent){
        mongoTemplate.save(loginEvent,"LoginEvent");
        return "";
    }
}
