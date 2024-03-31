package com.example.demo.service;

import com.example.demo.dao.ComponentDao;
import com.example.demo.dao.ComponentIdDao;
import com.example.demo.entity.Component;
import com.example.demo.entity.ComponentId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ComponentService {
    @Autowired
    ComponentDao componentDao;
    @Autowired
    ComponentIdDao componentIdDao;

    /**
     * 计算一段时间内某个页面的各个元素（根据元素名查询）的点击次数
     * @param date1: 开始时间
     * @param date2: 结束时间
     * @param pageTitle: 页面标题
     * @param type: 标识按哪个方面查询 1：按元素名查询 2：按元素类型查询 3：按元素id查询
     * @return : map,key("componentName","count")
     */
    public Map<String,Object> CountByComponentName(Date date1, Date date2,String pageTitle,String clientId,int type){
        //map用来保存各个元素名对应的点击次数
        Map<String,Integer> componentNameToCount;
        if(type == 1 || type == 2){
            List<Component> components = componentDao.findByClientIdIsAndTimeBetweenAndPageTitleIs(clientId,date1,date2,pageTitle);
            componentNameToCount = count(components,type);
        }else {
            List<ComponentId> components = componentIdDao.findByClientIdIsAndTimeBetweenAndPageTitleIs(clientId,date1,date2,pageTitle);
            componentNameToCount = count(components,type);
        }
        Set<String> componentNameSet = componentNameToCount.keySet();
        String[] componentName = componentNameSet.toArray(new String[0]);
        int[] count = new int[componentNameSet.size()];
        for (int i=0;i<componentNameSet.size();i++){
            count[i] = componentNameToCount.get(componentName[i]);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("componentName",componentName);
        map.put("count",count);
        return map;
    }
    private Map<String,Integer> count(List components,int type){
        Map<String,Integer> componentNameToCount = new HashMap<>();
        if(type==1){
            for(Object component:components){
                String key = ((Component)component).getComponentName();
                if(componentNameToCount.containsKey(key)){
                    int value = componentNameToCount.get(key);
                    componentNameToCount.put(key,++value);
                }else {
                    componentNameToCount.put(key,0);
                }
            }
        }else if(type == 2){
            for(Object component:components){
                String key = ((Component)component).getComponentType();
                if(componentNameToCount.containsKey(key)){
                    int value = componentNameToCount.get(key);
                    componentNameToCount.put(key,++value);
                }else {
                    componentNameToCount.put(key,0);
                }
            }
        }else {
            for(Object component:components){
                String key = ((ComponentId)component).getComponentId();
                if(componentNameToCount.containsKey(key)){
                    int value = componentNameToCount.get(key);
                    componentNameToCount.put(key,++value);
                }else {
                    componentNameToCount.put(key,0);
                }
            }
        }
        return componentNameToCount;
    }
}
