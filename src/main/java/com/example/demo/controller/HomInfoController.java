package com.example.demo.controller;

import com.example.demo.dao.LoginEventDao;
import com.example.demo.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.dao.UserInfoDao;
import com.example.demo.dao.HomeDao;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.example.demo.entity.UserInfo;

import javax.servlet.http.HttpSession;


@CrossOrigin
@Controller
public class HomInfoController{
    @Autowired
    HomeService homeService;
    @Autowired
    HomeDao  homeDao;
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    LoginEventDao loginEventDao;
    public String getAppKey(HttpSession session){
        return (String) session.getAttribute("appKey");
    }
    @GetMapping("/index")
    public String getMessage(Model model,HttpSession session){
        String AppKey=getAppKey(session);
        model.addAttribute("ClickToday",homeService.getVisited(AppKey));
        model.addAttribute("LoginToday",homeService.getLoginCount(1,AppKey));
        model.addAttribute("RegisterToday",homeService.getRegisterNum(1,AppKey));
        model.addAttribute("SevenLogin",homeService.getLoginCount(7,AppKey));
        model.addAttribute("Xarry",homeService.getDateArry());
        model.addAttribute("Yarry",homeService.getCountArry(AppKey));
        model.addAttribute("test1",homeService.getCountArry(AppKey));
        model.addAttribute("test2",homeService.getDateArry());
        model.addAttribute("StayTime",homeService.stayTimeAnalyse(AppKey));
        model.addAttribute("AppKey",AppKey);
        //System.out.println(AppKay);
        //System.out.println(homeService.getCountArry()[3]);
        //System.out.println(homeService.getDateArry()[3]);
        return "index";
    }
}
