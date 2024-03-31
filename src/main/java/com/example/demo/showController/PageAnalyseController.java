package com.example.demo.showController;

import com.example.demo.service.PageViewService;
import com.example.demo.util.DateCut;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PageAnalyseController {

    @Autowired
    ObjectMapper mapper;
    @Autowired
    PageViewService pageViewService;

    public String getAppKey(HttpSession session){
        return (String) session.getAttribute("appKey");
    }


    @RequestMapping("/pageVisitorCount")
    public String allPageVisitCount(Date date1, Date date2, HttpSession session) throws JsonProcessingException {

        String pageName[] = null;
        int pageCount[] = null;
        pageName = pageViewService.pageViewNameAnalyse(getAppKey(session),date1,date2);
        pageCount = pageViewService.userNumberAnalyse(getAppKey(session),date1,date2);
        Map<String,Object> map = new HashMap<>();
        map.put("xArray",pageName);
        map.put("yArray",pageCount);
        return mapper.writeValueAsString(map);
    }

    @RequestMapping("/pageViewCount")
    public String allPageViewCount(Date date1, Date date2, HttpSession session) throws JsonProcessingException {
        String pageName[] = pageViewService.pageViewNameAnalyse(getAppKey(session),date1,date2);
        int pageCount[] = pageViewService.pageViewNumberAnalyse(date1,date2,getAppKey(session));
        Map<String,Object> map = new HashMap<>();
        map.put("xArray",pageName);
        map.put("yArray",pageCount);
        return mapper.writeValueAsString(map);
    }

    @RequestMapping("/aveViewTime")
    public String aveViewTime(Date date1, Date date2, HttpSession session,int dateType) throws JsonProcessingException {
        String pageName[] = pageViewService.pageViewNameAnalyse(getAppKey(session),date1,date2);
        int pageCount[] = pageViewService.pageViewTAnalyse(date1,date2,getAppKey(session),dateType);
        Map<String,Object> map = new HashMap<>();
        map.put("xArray",pageName);
        map.put("yArray",pageCount);
        return mapper.writeValueAsString(map);
    }

    @RequestMapping("pageRedirect")
    public String pageRedirect(Date date1, Date date2,String pageTitle, HttpSession session) throws JsonProcessingException {
        String pageName[] = pageViewService.pageViewNameAnalyse(getAppKey(session),date1,date2);
        int pageCount[] = pageViewService.pageViewJumpAnalyse(date1,date2,pageTitle,getAppKey(session));
        Map<String,Object> map = new HashMap<>();
        map.put("xArray",pageName);
        map.put("yArray",pageCount);
        return mapper.writeValueAsString(map);
    }
    @RequestMapping("/specificPage")
    public String specificPage(String page,int dateType,Date date1,Date date2,int type,HttpSession session) throws JsonProcessingException, ParseException {
        if(type==0){return "{}";}
        String[] xArray = DateCut.dateCutByDateType(date1,date2,dateType);
        int[] yArray = null;
        switch (type){
            case 1: yArray = pageViewService.pageViewNumberAnalyseAppoint(getAppKey(session),page,date1,date2,dateType);
                    break;
            case 2:yArray = pageViewService.pageViewUserAnalyseAppoint(getAppKey(session),date1,date2,dateType,page);break;
            case 3:yArray = pageViewService.pageViewTAnalyseAppoint(date1,date2,getAppKey(session),dateType,page); break;
            default:break;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("xArray",xArray);
        map.put("yArray",yArray);
        return mapper.writeValueAsString(map);
    }


}
