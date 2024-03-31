package com.example.demo.service;

import com.example.demo.dao.CustomEventDao;
import com.example.demo.entity.CustomEvent;
import com.example.demo.entity.LoginEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.mapping.TextScore;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.demo.util.DateCut.dateCutByDateType;
import static com.example.demo.util.DateCut.getDays;
import static java.lang.System.exit;

@Service
public class CustomEventService {
    @Autowired
    CustomEventDao customEventDao;
    //该函数通过事件名获取某一事件属性名集合
    @Cacheable("getSXName")
    public  String[] getSXName(String client,String customEventName){
        CustomEvent customEvent = customEventDao.findFirstByClientIdIsAndCustomEventNameIs(client,customEventName);
        Set set = customEvent.getEventInfo().keySet();
        return (String[]) set.toArray(new String[0]);
    }
    //一段事件内某事件，相应属性的给定属性值出现的次数
    //横坐标  按日 周  月  年  对应  dateType分别为0 1 2 3
    @Cacheable("getCustomEventTimeXArray")
    public  String[] getCustomEventTimeXArray(String clientId, Date date1, Date date2, int dateType){
        String[] date = dateCutByDateType(date1,date2,dateType);
        return  date;
    }
    //一段事件内某事件，相应属性的给定属性值出现的次数
    //纵坐标
    @Cacheable("getCustomEventTimeYArray")
    public  int[] getCustomEventTimeYArray(String clientId,Date date1,Date date2,int dateType,String customEventName,String SXName,String SXValue) throws ParseException {
        String[] date = dateCutByDateType(date1,date2,dateType);
        int days = getDays(date1,date2);
        int i,j,z,n;//循环次数
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
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
            List<CustomEvent> customEvents = customEventDao.findByClientIdIsAndTimeBetweenAndCustomEventNameIs(clientId,start,end,customEventName);
            for ( z = 0; z < customEvents.size(); z++) {
                   if (customEvents.get(z).getEventInfo().get(SXName).toString().equals(SXValue)){ number[i]++;}
                }
            //System.out.println(date[i]+"       "+date[j]+"    "+i+"   "+number[i]);

        }


        return  number;
    }

    //该函数为某段时间内给定事件的给定属性的属性值集合------XArray
    @Cacheable("getCustomEventNameValueXArray")
    public  String[] getCustomEventNameValueXArray(String clientId,Date date1,Date date2,String customEventName,String SXName) throws ParseException {
        List<CustomEvent> customEvents = customEventDao.findByClientIdIsAndTimeBetweenAndCustomEventNameIs(clientId,date1,date2,customEventName);
        int i;
        HashSet<String> attribteName = new LinkedHashSet<>();
        for (i=0;i<customEvents.size();i++){
            attribteName.add(customEvents.get(i).getEventInfo().get(SXName).toString());
        }
        String[] Name = attribteName.toArray(new String[0]);
        return  Name;
    }


    @Cacheable("getCustomEventNameValueYArray")
    public  int[] getCustomEventNameValueYArray(String clientId,Date date1,Date date2,String customEventName,String SXName) throws ParseException {
             String[] name= getCustomEventNameValueXArray(clientId,date1,date2,customEventName,SXName);
            List<CustomEvent> customEvents = customEventDao.findByClientIdIsAndTimeBetweenAndCustomEventNameIs(clientId,date1,date2,customEventName);
            int[] Count = new int[name.length];
        for(int i=0;i<customEvents.size();i++){
            for(int j=0;j<name.length;j++){
                if(customEvents.get(i).getEventInfo().get(SXName).equals(name[j]))Count[j]++;
            }
        }
        return  Count;
    }
}
