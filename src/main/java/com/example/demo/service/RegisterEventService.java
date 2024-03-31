package com.example.demo.service;

import com.example.demo.dao.RegisterEventDao;
import com.example.demo.entity.RegisterEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.demo.util.DateCut.dateCutByDateType;
import static com.example.demo.util.DateCut.getDays;
import static java.lang.Math.ceil;
import static java.lang.Math.floor;

@Service
public class RegisterEventService {
    @Autowired
    RegisterEventDao registerEventDao;

    /**
     * 获取一段时间的新增用户数
     * @param clientId
     * @param date1
     * @param date2
     * @param dateType
     * @return
     * @throws ParseException
     */
    @Cacheable(value = "newUserNumber")
    public int[] newUserNumberAnalyse(String clientId, Date date1, Date date2,int dateType) throws ParseException {
        String[] date = dateCutByDateType(date1, date2,dateType);
        int days = getDays(date1, date2);
        int[] number = new int[days+10];
        int i,j=0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        Date start,end;
        if(dateType==0){
            for (i = 0; i <days; i++) {
                j=i+1;
                start=sdf.parse(date[i]);
                end = sdf.parse(date[j]);
                number[i] = registerEventDao.countRegisterEventByClientIdIsAndTimeBetween(clientId,start, end);
                //System.out.println(date[i]+"       "+date[j]+"    "+number[i]);
            }
        }
        else if(dateType==1){
            for (i = 0; i <days/7+2; i++) {
                j=i+1;
                start=sdf.parse(date[i]);
                end = sdf.parse(date[j]);
                number[i] = registerEventDao.countRegisterEventByClientIdIsAndTimeBetween(clientId,start, end);
               System.out.println(date[i]+"       "+date[j]+"    "+number[i]);
            }
        }
        else if(dateType==2){
            for (i = 0; i <ceil(days/30)+1; i++) {
                j=i+1;
                start=sdf.parse(date[i]);
                end = sdf.parse(date[j]);
                number[i] = registerEventDao.countRegisterEventByClientIdIsAndTimeBetween(clientId,start, end);
               //System.out.println(date[i]+"       "+date[j]+"    "+number[i]);
            }
        }
        else {
            for (i = 0; i <ceil(days/365)+1; i++) {
                j=i+1;
                start=sdf.parse(date[i]);
                end = sdf.parse(date[j]);
                number[i] = registerEventDao.countRegisterEventByClientIdIsAndTimeBetween(clientId,start, end);
               // System.out.println(date[i]+"       "+date[j]+"    "+number[i]);
            }
        }
        number[i]=-1;//-1结束标志
        int l = -1;
        while(number[++l]!=-1){}
        int[] userId = new int[l--];
        for(int k=0;k<=l;k++){
            userId[k]=number[k];
        }
//        for(int s:userId){
//            System.out.println(s);
//        }
        return userId;

    }

    //获取新注册用户的userID传至UserInfoService以用来得到该用户的属性
    @Cacheable("RegistergetUserID")
    public int[] getUserID(String clientId, Date date1, Date date2){
        //查询的结果放入registerEvents中
        List<RegisterEvent> registerEvents = registerEventDao.findByClientIdIsAndTimeBetween(clientId, date1, date2);
        //number==结果个数
        int number = registerEventDao.countRegisterEventByClientIdIsAndTimeBetween(clientId, date1, date2);
        int[] userID = new int[number+10];
        int i;
        for( i=0;i<number;i++){
            userID[i] = registerEvents.get(i).getUserId();
        }
        userID[i]=-1;//规定-1时结束标志
        int j = -1;
        while(userID[++j]!=-1){}
        int[] userId = new int[j--];
        for(int k=0;k<=j;k++){
            userId[k]=userID[k];
        }
        return userId;
    }

}