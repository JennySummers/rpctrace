package com.example.demo.showController;

import com.example.demo.service.LoginEventService;
import com.example.demo.service.RegisterEventService;
import com.example.demo.service.UserInfoService;
import com.example.demo.util.DateCut;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
public class UsersAnalyseController {

    @Autowired
    ObjectMapper mapper;
    @Autowired
    RegisterEventService registerEventService;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    LoginEventService loginEventService;


    public String getAppKey(HttpSession session){
        return (String) session.getAttribute("appKey");
    }

    /**
     * 获取一段时间内有多少用户登录过系统
     * @param date1 开始时间
     * @param date2 结束时间
     * @return
     */
    @RequestMapping("/activeUser")
    public String activeUsersCount(Date date1,Date date2,int dateType,HttpSession session) throws ParseException, JsonProcessingException {
        String[] xArray = DateCut.dateStringFormat(date1,date2,dateType);
        int[] yArray = loginEventService.getLoginEventYArray(getAppKey(session),date1,date2,dateType);
        Map<String,Object> map = new HashMap<>();
        map.put("xArray",xArray);
        map.put("yArray",yArray);
        return mapper.writeValueAsString(map);
    }

    @RequestMapping("/silenceUser")
    public String silenceUsersCount(Date date1,Date date2,int dateType,HttpSession session) throws ParseException, JsonProcessingException {
        String[] xArray = DateCut.dateStringFormat(date1,date2,dateType);
        int[] yArray = loginEventService.getYArray(getAppKey(session),date1,date2,dateType);
        Map<String,Object> map = new HashMap<>();
        map.put("xArray",xArray);
        map.put("yArray",yArray);
        return mapper.writeValueAsString(map);
    }



    /**
     *获取一段时间内平均每个用户登录几次
     * @param date1
     * @param date2
     * @return 一个整数
     */
    @RequestMapping("/averLoginNumber")
    public String averLoginNumber(Date date1,Date date2){

        return "";
    }

    /**
     *用户登录时间段分析
     * @param date1
     * @param date2
     * @return 一个整数
     */
    @RequestMapping("/loginTime")
    public String loginTime(Date date1,Date date2,HttpSession session) throws JsonProcessingException {
        String[] xArray = loginEventService.getTimeXArray();
        int[] yArray = loginEventService.getTimeYArray(getAppKey(session),date1,date2);
        Map<String,Object> map = new HashMap<>();
        map.put("xArray",xArray);
        map.put("yArray",yArray);
        return mapper.writeValueAsString(map);
    }

    /**
     * 用户登录时间段分析
     * @param date1
     * @param date2
     * @return
     */
    @RequestMapping("/newUser")
    @ResponseBody
    public String newUser(Date date1,Date date2,int dateType,HttpSession session) throws ParseException, JsonProcessingException {
        String[] xArray = DateCut.dateStringFormat(date1,date2,dateType);
        int[] yArray = registerEventService.newUserNumberAnalyse(getAppKey(session),date1,date2,dateType);
        Map<String,Object> map = new HashMap<>();
        map.put("xArray",xArray);
        map.put("yArray",yArray);
        return mapper.writeValueAsString(map);
    }


    /**
     * 获取用户信息的属性名集合
     * @return
     */
    @RequestMapping("/getUserInfo")
    public String userAttribute(HttpSession session) throws JsonProcessingException {
        String[] attr = userInfoService.getUserInfo(getAppKey(session));
        Map<String,Object> map = new HashMap<>();
        map.put("attr",attr);
       return mapper.writeValueAsString(map);
    }

    @RequestMapping("/UserInfoAnalyse")
    public String userInfoAnalyse(String attributeName,Date date1,Date date2,int type,HttpSession session) throws JsonProcessingException {
        if(type == 0){return "{}";}else {type--;}

        String[] xArray = userInfoService.returnXArray(attributeName,getAppKey(session),date1,date2);
        int[] yArray = userInfoService.returnYNumber(attributeName,getAppKey(session),date1,date2,type);
        Map<String,Object> map = new HashMap<>();
        map.put("xArray",xArray);
        map.put("yArray",yArray);
        return mapper.writeValueAsString(map);
    }


}
