package com.example.demo.showController;

import com.example.demo.dao.PageDao;
import com.example.demo.entity.Page;
import com.example.demo.service.ComponentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.*;


@RestController
public class ComponentAnalyseController {
    @Autowired
    ObjectMapper mapper;
    @Autowired
    ComponentService componentService;
    @Autowired
    UsersAnalyseController usersAnalyseController;
    @Autowired
    PageDao pageDao;

    @RequestMapping("/byComponentName")
    public String byComponentName(HttpSession session, Date date1,Date date2,String pageTitle) throws JsonProcessingException {
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> map1 = componentService.CountByComponentName(date1,date2,pageTitle,usersAnalyseController.getAppKey(session),1);
        map.put("xArray",map1.get("componentName"));
        map.put("yArray",map1.get("count"));
        return mapper.writeValueAsString(map);
    }

    @RequestMapping("/byComponentType")
    public String byComponentType(HttpSession session, Date date1,Date date2,String pageTitle) throws JsonProcessingException {
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> map1 = componentService.CountByComponentName(date1,date2,pageTitle,usersAnalyseController.getAppKey(session),2);
        map.put("xArray",map1.get("componentName"));
        map.put("yArray",map1.get("count"));
        return mapper.writeValueAsString(map);
    }

    @RequestMapping("/byComponentId")
    public String byComponentId(HttpSession session, Date date1,Date date2,String pageTitle) throws JsonProcessingException {
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> map1 = componentService.CountByComponentName(date1,date2,pageTitle,usersAnalyseController.getAppKey(session),3);
        map.put("xArray",map1.get("componentName"));
        map.put("yArray",map1.get("count"));
        return mapper.writeValueAsString(map);
    }
    @RequestMapping("/getPage")
    public String getPage(HttpSession session) throws JsonProcessingException {
        List<Page> pages = pageDao.findByClientIdIs(usersAnalyseController.getAppKey(session));
        List<String> list = new LinkedList<>();
        for(Page page:pages){
            list.add(page.getPageUrl());
        }
        String[] page = list.toArray(new String[0]);
        Map<String,Object> map = new HashMap<>();
        map.put("attr",page);
        return mapper.writeValueAsString(map);
    }


}
