package com.example.demo.service;

import com.example.demo.dao.CustomEventDao;
import com.example.demo.dao.CustomEventNameDao;
import com.example.demo.entity.CustomEventName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomEventNameService {
    @Autowired
    CustomEventNameDao customEventNameDao;
    //该函数用来获取所有自定义事件名
    public  String[] getCustomEventName(String clientId){
        List<CustomEventName> customEventNames = customEventNameDao.findAllByClientIdIs(clientId);
        String[] customEventName = new String[customEventNames.size()];
        for(int i=0;i<customEventNames.size();i++){
            customEventName[i] = customEventNames.get(i).getCustomEventName();
            //System.out.println(customEventName[i]);
        }
        return  customEventName;
    }
}
