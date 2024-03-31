package com.example.demo.service;

import com.example.demo.dao.LoginEventDao;
import com.example.demo.dao.UserInfoDao;
import com.example.demo.entity.LoginEvent;
import com.example.demo.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.demo.util.DateCut.dateCutByDateType;
import static com.example.demo.util.DateCut.getDays;

@Service
public class LoginEventService {
    @Autowired
    LoginEventDao loginEventDao;
    @Autowired
    UserInfoDao userInfoDao;

    public int getLoginCount(String clientId,Date date1,Date date2){
        List<LoginEvent> aa = loginEventDao.findByClientIdIsAndTimeBetween(clientId,date1,date2);
        LinkedHashSet<Integer> userIds = new LinkedHashSet<>();
        for(LoginEvent e:aa){
            userIds.add(e.getUserId());
        }
        return userIds.size();
    }


    /*----------------活跃人数------------------*/
    public  String[] getLoginEventXArray(String clientId,Date date1,Date date2,int dateType){
        String[] date = dateCutByDateType(date1,date2,dateType);
        return  date;
    }
    @Cacheable("getLoginEventYArray")
    public  int[] getLoginEventYArray(String clientId,Date date1,Date date2,int dateType) throws ParseException {
        String[] date = dateCutByDateType(date1,date2,dateType);
        int days = getDays(date1,date2);
        int i,j,z,n;//循环次数
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        HashSet<Integer> userID = new LinkedHashSet<> ();
        Date start,end;
        int[] number;
        if(dateType==0){number = new int[days]; n= days;}
        else  if(dateType==1){number = new int[days/7+2]; n= days/7+2;}
        else  if(dateType==2){number = new int[days/30+1]; n= days/30+1;}
        else  {number = new int[days/365+1]; n= days/365+1;}
        for (i = 0; i < n; i++) {
                j = i + 1;
                start = sdf.parse(date[i]);
                end = sdf.parse(date[j]);
                List<LoginEvent> loginEvents = loginEventDao.findByClientIdIsAndTimeBetween(clientId, start, end);
                for ( z = 0; z < loginEvents.size(); z++) {
                    userID.add(loginEvents.get(z).getUserId());
                }
                number[i] = userID.size();
                userID.clear();
//            System.out.println(date[i]+"       "+date[j]+"    "+number[i]);
        }
        return  number;
    }

    /*-------------------沉默用户-------------------------*/
   // getXArray与活跃用户一样，即为getLoginEventXArray
    @Cacheable("getYArray")
    public  int[] getYArray(String clientId,Date date1,Date date2,int dateType) throws ParseException {
        String[] date = dateCutByDateType(date1,date2,dateType);
        int days = getDays(date1,date2);
        int i,j,z,n;//循环次数
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        HashSet<Integer> userID = new LinkedHashSet<> ();
        Date start,end;
        int[] number;int totalNumber;
        if(dateType==0){number = new int[days]; n= days;}
        else  if(dateType==1){number = new int[days/7+2]; n= days/7+2;}
        else  if(dateType==2){number = new int[days/30+1]; n= days/30+1;}
        else  {number = new int[days/365+1]; n= days/365+1;}

        List<UserInfo> userInfos;
        int[] userId = getUserID(clientId,date1,date2);
        Collection<Integer> userID1 = new HashSet<>();
        for (int s:userId){userID1.add(s);}
        userInfos = userInfoDao.findByUserIdInAndClientIdIs(userID1,clientId);

        //List<UserInfo> userInfos = userInfoDao.findByTimeBefore(date2);
        HashSet<Integer> userTotalID = new LinkedHashSet<> ();
        for (int a=0;a<userInfos.size();a++){
            userTotalID.add(userInfos.get(a).getUserId());
        }
        totalNumber=userTotalID.size();
        for (i = 0; i < n; i++) {
            j = i + 1;
            start = sdf.parse(date[i]);
            end = sdf.parse(date[j]);
            List<LoginEvent> loginEvents = loginEventDao.findByClientIdIsAndTimeBetween(clientId, start, end);
            for ( z = 0; z < loginEvents.size(); z++) {
                userID.add(loginEvents.get(z).getUserId());
            }
            number[i]=totalNumber- userID.size();
            userID.clear();
//            System.out.println(date[i]+"       "+date[j]+"    "+number[i]);
        }
        return  number;
    }
    /*--------------------用户登录时间------------------------------*/
    public  String[]  getTimeXArray(){
        String[] XArray = {"00:00`01:59","02:00`03:59","04:00`05:59","06:00`07:59","08:00`09:59","10:00`11:59","12:00`13:59","14:00`15:59","16:00`17:59","18:00`19:59","20:00`21:59","22:00`23:59"};
        return  XArray;
    }
    @Cacheable("getTimeYArray")
    public  int[]  getTimeYArray(String clientId,Date date1,Date date2){
        List<LoginEvent> loginEvents = loginEventDao.findByClientIdIsAndTimeBetween(clientId,date1,date2);
        Date[] date = new Date[loginEvents.size()];
        int i;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        String[] sdate =  new String[loginEvents.size()];
        for(i=0;i<loginEvents.size();i++){
            date[i]=loginEvents.get(i).getTime();
            sdate[i]=sdf.format(date[i]);
            //System.out.println(date[i]+"    "+sdate[i]);
            sdate[i]=sdate[i].substring(11,16);
            //  System.out.println(sdate[i]);
        }
        String[] timeBetween =getTimeXArray();
        for (i=0;i<timeBetween.length;i++) {timeBetween[i]=timeBetween[i].substring(6,11);}
        //for (String s:timeBetween) System.out.println(s);
        int[] number = new int[12];//时间段两个小时为一个间隔，一个12组间隔
        for (i=0;i<12;i++){number[i]=0;}
        for(i=0;i<sdate.length;i++){
            for(int j=0;j<11;j++){
               if(sdate[i].compareTo(timeBetween[0])<=0){number[0]++;break;}
               else if(sdate[i].compareTo(timeBetween[j])>0&&sdate[i].compareTo(timeBetween[j+1])<=0){number[j+1]++;break;}
            }
        }
        return number;
    }
    //获取新注册用户的userID传至UserInfoService以用来得到该用户的属性
    @Cacheable("getUserID")
    public int[] getUserID(String clientId, Date date1, Date date2){
        //查询的结果放入registerEvents中
        List<LoginEvent> loginEvents = loginEventDao.findByClientIdIsAndTimeBetween(clientId, date1, date2);
        //number==结果个数
        int number = loginEventDao.countLoginEventByClientIdIsAndTimeBetween(clientId, date1, date2);
        int[] userID = new int[number+10];
        int i;
        for( i=0;i<number;i++){
            userID[i] = loginEvents.get(i).getUserId();
        }
        userID[i]=-1;//规定-1时结束标志
        return  userID;
    }

}
