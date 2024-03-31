package com.example.demo.showController;

import com.example.demo.service.CustomEventNameService;
import com.example.demo.service.CustomEventService;
import com.example.demo.service.PageViewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
public class CustomEventAnalyseController {
    @Autowired
    CustomEventService customEventService;
    @Autowired
    CustomEventNameService customEventNameService;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    UsersAnalyseController usersAnalyseController;

    @RequestMapping("/getEventName")
    public String getEventName(HttpSession session) throws JsonProcessingException {
        String[] attr = customEventNameService.getCustomEventName(usersAnalyseController.getAppKey(session));
        Map<String,Object> map = new HashMap<>();
        map.put("attr",attr);
        return mapper.writeValueAsString(map);
    }

    @RequestMapping("/getAttrName")
    public String getAttrName(HttpSession session,String eventName) throws JsonProcessingException {
        String[] attr = customEventService.getSXName(usersAnalyseController.getAppKey(session),eventName);
        Map<String,Object> map = new HashMap<>();
        map.put("attr",attr);
        return mapper.writeValueAsString(map);
    }
    @RequestMapping("/getAttrVal")
    public String getAttrVal(HttpSession session,String eventName,String attrName) throws JsonProcessingException, ParseException {
         Date date1 = new Date();
         Date date2 = new Date(date1.getYear()-1,date1.getMonth(),date1.getDay());
         String[] attr = customEventService.getCustomEventNameValueXArray(usersAnalyseController.getAppKey(session),date2,date1,eventName,attrName);
         Map<String,Object> map = new HashMap<>();
         map.put("attr",attr);
         return mapper.writeValueAsString(map);
     }
    @RequestMapping("/specificAttrVal")
    public String specificAttrVal(Date date1,Date date2,String eventName,String attrName,String attrVal,int dateType,HttpSession session) throws ParseException, JsonProcessingException {
        String[] xArray = customEventService.getCustomEventTimeXArray(usersAnalyseController.getAppKey(session),date1,date2,dateType);
        int[] yArray = customEventService.getCustomEventTimeYArray(usersAnalyseController.getAppKey(session),
                date1,date2,dateType,eventName,attrName,attrVal);
        Map<String,Object> map = new HashMap<>();
        map.put("xArray",xArray);
        map.put("yArray",yArray);
        return mapper.writeValueAsString(map);
    }
    @RequestMapping("/allAttrVal")
    public String allAttrVal(Date date1,Date date2,String eventName,String attrName,HttpSession session) throws JsonProcessingException, ParseException {
         int[] attr = customEventService.getCustomEventNameValueYArray(usersAnalyseController.getAppKey(session),
                 date1,date2,eventName,attrName);
         String[] xArray = customEventService.getCustomEventNameValueXArray(usersAnalyseController.getAppKey(session),
                 date1,date2,eventName,attrName);
         Map<String,Object> map = new HashMap<>();
         map.put("xArray",xArray);
         map.put("yArray",attr);
         return mapper.writeValueAsString(map);
    }
}
