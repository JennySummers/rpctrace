package com.example.demo.service;

import com.example.demo.dao.*;
import com.example.demo.entity.CustomEventName;
import com.example.demo.entity.LoginEvent;
import com.example.demo.entity.PageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class HomeService {
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    LoginEventDao loginEventDao;
    @Autowired
    RegisterEventDao registerEventDao;
    @Autowired
    HomeDao homeDao;
    @Autowired
    PageViewDao pageViewDao;
    public int getVisited(String AppKey)
    {
        List<PageView> pageViews=homeDao.findPageViewByClientId(AppKey);
        int count=pageViews.size();
        return count;
    }
    public int getLoginCount(int x,String AppKey)
    {
        Date date1=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date1);
        calendar.add(calendar.DATE,-x);//把日期往后增加一天.整数往后推,负数往前移动
        Date date2=calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString1 = formatter.format(date1);
        String dateString2 = formatter.format(date2);
        dateString1+=" 23:59:59";
        dateString2+=" 23:59:59";
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //加上时间
        //必须捕获异常
        try {
            date1=sDateFormat.parse(dateString1);
        } catch(ParseException px) {
            px.printStackTrace();
        }
        try {
            date2=sDateFormat.parse(dateString2);
        } catch(ParseException px) {
            px.printStackTrace();
        }
        List<LoginEvent> loginEvents=loginEventDao.findByClientIdIsAndTimeBetween(AppKey,date2,date1);
        return loginEvents.size();
    }
    public String getDateArry()
    {
        String arr = new String();
        int i;
        for(i=19;i>=0;i--)
        {
            Date date=new Date();//取时间
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE,-i);//把日期往后增加一天.整数往后推,负数往前移动
            date=calendar.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(date);
            arr=arr+dateString;
            arr+=',';
            //System.out.println(arr[i]);
        }
        return arr;
    }
    public String getCountArry(String AppKey)
    {
        String arr = new String();
        int i;
        for(i=19;i>=0;i--)
        {
            int x=getLoginCount(i+1,AppKey)-getLoginCount(i,AppKey);

            arr+=String.valueOf(x);
            arr+=',';
            //System.out.println(arr[i]);
        }
        return arr;
    }
    public int getRegisterNum(int x,String AppKey)
    {
        Date date1=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date1);
        calendar.add(calendar.DATE,-x);//把日期往后增加一天.整数往后推,负数往前移动
        Date date2=calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString1 = formatter.format(date1);
        String dateString2 = formatter.format(date2);
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //加上时间
        int anser = 0;
        anser=registerEventDao.findByClientIdIsAndTimeBetween(AppKey,date2,date1).size();
        return anser;
    }
    public String stayTimeAnalyse(String AppKey)
    {
        String ans=new String();
        int less1mimute=0;
        int less10minute=0;
        int less30minute=0;
        int less1hour=0;
        int less3hour=0;
        int over3hour=0;
        List<PageView> pageViews=pageViewDao.findAllByClientId(AppKey);
        Date date1,date2;
        int i;
        for(i=0;i<pageViews.size();i++)
        {
            date1=pageViews.get(i).getTime();
            date2=pageViews.get(i).getEndTime();
            long a;
            if(date1!=null&&date2!=null)
            {
                a=(date2.getTime()-date1.getTime())/1000;
            }
            else
            {
                a = 10845;
            }
            if(a<=60)
            {
                less1mimute++;
                continue;
            }
            if(a<=600)
            {
                less10minute++;
                continue;
            }
            if(a<=1800)
            {
                less30minute++;
                continue;
            }
            if(a<=3600)
            {
                less1hour++;
                continue;
            }
            if(a<=10800)
            {
                less3hour++;
                continue;
            }
            over3hour++;
        }
        ans+=String.valueOf(less1mimute);
        ans+=',';
        ans+=String.valueOf(less10minute);
        ans+=',';
        ans+=String.valueOf(less30minute);
        ans+=',';
        ans+=String.valueOf(less1hour);
        ans+=',';
        ans+=String.valueOf(less3hour);
        ans+=',';
        ans+=String.valueOf(over3hour);
        return  ans;
    }
}
