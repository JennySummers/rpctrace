package com.example.demo.controller;

import com.example.demo.dao.CustomEventNameDao;
import com.example.demo.entity.CustomEvent;
import com.example.demo.entity.CustomEventName;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin
@Controller
public class CustomEventController {
    private static final Logger logger = LoggerFactory.getLogger(CustomEventController.class);

    @Autowired
    ObjectMapper mapper;
    @Autowired
    CustomEventNameDao customEventNameDao;
    @Autowired
    MongoTemplate mongoTemplate;

    @ResponseBody
    @PostMapping("/customEvent")
    public String customEvent(@RequestBody List<CustomEvent> customEvents) throws IOException {
        logger.info(customEvents.toString());
        for (CustomEvent customEvent : customEvents) {
            customEvent.setEventName("CustomEvent");
            mongoTemplate.save(customEvent,"CustomEvent");
//            Document doc = Document.parse(mapper.writeValueAsString(customEvent));
            if(customEventNameDao.findByCustomEventNameIsAndClientIdIs(customEvent.getCustomEventName(),customEvent.getClientId())==null){
                mongoTemplate.save(new CustomEventName(customEvent.getCustomEventName(),customEvent.getClientId()));
            }
        }

        return "";
    }
}
