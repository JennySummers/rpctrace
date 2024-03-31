package com.example.demo.service;

import com.example.demo.dao.LoginEventDao;
import com.example.demo.dao.RegisterEventDao;
import com.example.demo.dao.UserInfoDao;
import com.example.demo.entity.LoginEvent;
import com.example.demo.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserInfoService {
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    LoginEventDao loginEventDao;
    @Autowired
    LoginEventService loginEventService;
    @Autowired
    RegisterEventDao registerEventDao;
    @Autowired
    RegisterEventService registerEventService;
    @Cacheable("getUserInfo")
    public String[] getUserInfo(String clientId){
        //获取某一个用户的userInfo
        UserInfo userInfos = userInfoDao.findFirstByClientIdIs(clientId);
        Set set = userInfos.getUserInfo().keySet();
        return (String[]) set.toArray(new String[0]);
    }
    //age横坐标
    public String[] getAgeArray(){
        String [] age ={"0`10","10`20","20`30","30`40","40`50","60`70","70`80","80+"};
        return  age;
    }
    //对应age范围内人数   0新增用户  1活跃用户  2 沉默用户
    @Cacheable("getAgeNumberArray")
    public  int[]  getAgeNumberArray(String clientId, Date date1, Date date2,int Type){
        int[] ageCount = new int[8];
        List<UserInfo> userInfos;
        if(Type==0){
            int[] userId = registerEventService.getUserID(clientId, date1, date2);
            Collection<Integer> userID1 = new HashSet<>();
            for (int i = 0; i < userId.length; i++) {
                userID1.add(userId[i]);
            }
            userInfos = userInfoDao.findByUserIdInAndClientIdIs(userID1, clientId);
            //userInfos = userInfoDao.findByClientIdIsAndTimeBetween(clientId,date1,date2);
        }
        else{
            int[] userID = loginEventService.getUserID(clientId,date1,date2);
            Collection<Integer> userID1 = new HashSet<>();
            for (int s:userID){userID1.add(s);}
             userInfos = userInfoDao.findByUserIdInAndClientIdIs(userID1,clientId);
        }
        //List<UserInfo> userInfos = userInfoDao.findByClientIdIsAndTimeBetween(clientId,date1,date2);
        for(UserInfo userInfo:userInfos){
            //ageCount[Integer.parseInt((String)(Integer.parseInt((String) userInfo.getUserInfo().get("age"))/10))]++;
            int age = 0;
            if(userInfo.getUserInfo().get("age")!=null)
            {
                age=Integer.parseInt(userInfo.getUserInfo().get("age").toString());
            }
            age = age/10;
            if(age<7)ageCount[age]++;
            else  ageCount[7]++;
        }
       int[] Total = new  int[ageCount.length];
        List<UserInfo> userInfos1 = userInfoDao.findByClientIdIs(clientId);
        for(int i=0;i<userInfos1.size();i++)
        {
            int age = 0;
            if(userInfos1.get(i).getUserInfo().get("age")!=null)
            {
                age = Integer.parseInt(userInfos1.get(i).getUserInfo().get("age").toString());
            }
            age = age/10;
            if(age<7)Total[age]++;
            else  Total[7]++;
        }
        if(Type==2){
            for (int i=0;i<ageCount.length;i++){
                ageCount[i]=Total[i]-ageCount[i];
            }
        }
        return  ageCount;
    }
    //非年龄横坐标
    @Cacheable("notAgeArray")
    public String[] notAgeArray(String attribute, String clientId, Date date1, Date date2){
        List<UserInfo> userInfos;
        int[] userID = loginEventService.getUserID(clientId,date1,date2);
        Collection<Integer> userID1 = new HashSet<>();
        for (int s:userID){userID1.add(s);}
        userInfos = userInfoDao.findByUserIdInAndClientIdIs(userID1,clientId);

//        List<UserInfo> userInfos = userInfoDao.findByClientIdIsAndTimeBetween(clientId,date1,date2);
        int i;
        HashSet<String> attribteName = new LinkedHashSet<>();
        for(i=0;i<userInfos.size();i++){
            if(userInfos.get(i).getUserInfo().get(attribute)!=null)
            {
                //userInfos.get(i).getUserInfo().get(attribute)
                attribteName.add(userInfos.get(i).getUserInfo().get(attribute).toString()) ;
            }
        }
        String[] Name = attribteName.toArray(new String[0]);
        return Name;
    }
    //非年龄属性人员个数
    @Cacheable("notAgeNumber")
    public int[] notAgeNumber(String attribute, String clientId, Date date1, Date date2,int Type){
        String[] attributeName = notAgeArray(attribute,clientId,date1,date2);
        int[] number = new int[attributeName.length];
        List<UserInfo> userInfos;
        if(Type==0){
            int[] userId = registerEventService.getUserID(clientId, date1, date2);
            Collection<Integer> userID1 = new HashSet<>();
            for (int i = 0; i < userId.length; i++) {
                userID1.add(userId[i]);
            }
            userInfos = userInfoDao.findByUserIdInAndClientIdIs(userID1, clientId);
            //userInfos = userInfoDao.findByClientIdIsAndTimeBetween(clientId,date1,date2);
        }
        else{
            int[] userID = loginEventService.getUserID(clientId,date1,date2);
            Collection<Integer> userID1 = new HashSet<>();
            for (int i=0;i<userID.length;i++){userID1.add(userID[i]);}

            userInfos = userInfoDao.findByUserIdInAndClientIdIs(userID1,clientId);
        }
        //List<UserInfo> userInfos = userInfoDao.findByClientIdIsAndTimeBetween(clientId,date1,date2);
        for(int i=0;i<attributeName.length;i++){
            for(int j=0;j<userInfos.size();j++) {
                if(userInfos.get(j).getUserInfo().get(attribute)!=null&&attributeName[i]!=null) {
                    if (userInfos.get(j).getUserInfo().get(attribute).toString().equals(attributeName[i])) {
                        number[i]++;
                    }
                }
            }
        }
        int[] Total= new int[number.length];
        List<UserInfo> userInfos1 = userInfoDao.findByClientIdIs(clientId);
        for(int i=0;i<attributeName.length;i++){
            for(int j=0;j<userInfos1.size();j++) {
                if(userInfos1.get(j).getUserInfo().get(attribute)!=null&&attributeName[i]!=null) {
                    if (userInfos1.get(j).getUserInfo().get(attribute).toString().equals(attributeName[i])) {
                        Total[i]++;
                    }
                }
            }
        }

        if(Type==2){
            for (int i=0;i<number.length;i++){number[i]=Total[i]-number[i];}
        }
        return number;
    }
/*-------------------总的使用方法-----------------------------------*/
    public String[]  returnXArray(String attribute, String clientId, Date date1, Date date2){
        if(attribute.equals("age")) return getAgeArray();
        else  return  notAgeArray(attribute,clientId,date1,date2);
    }
    public int[]  returnYNumber(String attribute, String clientId, Date date1, Date date2,int Type){
        if(attribute.equals("age")) return  getAgeNumberArray(clientId,date1,date2,Type);
        else  return  notAgeNumber(attribute,clientId,date1,date2, Type);
    }
}
