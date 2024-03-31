package com.example.demo;

import com.example.demo.dao.*;
import com.example.demo.entity.*;
import com.example.demo.service.ComponentService;
import com.example.demo.service.PageViewService;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.CheckedOutputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    CustomEventDao customEventDao;
    @Autowired
    ClientAppKeyDao clientAppKeyDao;
    @Autowired
    PageViewService pageViewService;
    @Autowired
    ComponentService componentService;
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    LoginEventDao loginEventDao;
    @Autowired
    PageViewDao pageViewDao;
    @Test
    public void contextLoads() throws ParseException {
        String startStr = "2019.3.9 00:00:00";
        String endStr = "2019.3.20 23:59:59";
        String TIME = "2019.1.1 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        for(int i=1;i<4;i++){
            for(int j=1;j<31;j++){
                String time = TIME.substring(0,5)+i+"."+j+TIME.substring(8);
                System.out.println(time);
            }
        }
        Date start = sdf.parse(startStr);
        Date end = sdf.parse(endStr);

        // 测试component按名查询
//        Map map = componentService.CountByComponentName(start,end,"/index2.html","123456789",3);
//        System.out.println(map.toString());
//        int[] a = (int[]) map.get("count");
//        String[] b = (String[]) map.get("componentName");
//        for(int i=0;i<a.length;i++){
//            System.out.println(b[i]+":"+a[i]);
//        }
        //end
    }
    @Test
    public void addPage() throws ParseException {
        String clientId = "123456789";
        String time = "2019.1.1 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        String pageName = "page";
        for(int i = 1;i<=10;i++){
            //mongoTemplate.save(new Page(pageName+ i,clientId));
        }

    }

    @Test
    public void RegisterAndLogin() throws ParseException {
        String clientId = "123456789";
        String user = "user";
        int userId = 0;

        String time = "2019.1.1 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        Date startTime = sdf.parse(time);
        List<RegisterEvent> registerEvents = new LinkedList<>();
        List<LoginEvent> loginEvents = new LinkedList<>();
        List<UserInfo> userInfos = new LinkedList<>();
        for(int j=0;j<120;j++){
            int randomInt = (int) (Math.random()*10);
            for (int k = 0;k<randomInt;k++){
                RegisterEvent registerEvent = new RegisterEvent();
                LoginEvent loginEvent = new LoginEvent();
                UserInfo userInfo = new UserInfo();
                registerEvent.setId(new ObjectId());
                registerEvent.setClientId(clientId);
                registerEvent.setUserId(userId);
                userInfo.setId(new ObjectId());
                userInfo.setClientId(clientId);
                userInfo.setUserId(userId);
                Map<String,String> map = new HashMap<>();
                map.put("age",""+(int) (Math.random()*90));
                map.put("occupy","job"+(int) (Math.random()*10));
                map.put("level","level"+(int) (Math.random()*10));
                map.put("address","location"+(int) (Math.random()*10));
                loginEvent.setId(new ObjectId());
                loginEvent.setClientId(clientId);
                loginEvent.setUserId(userId++);
                Date date1 = new Date(startTime.getTime());
                Date date2 = new Date(startTime.getTime());
                int second = (int) ((Math.random()*10)*8640);
                date1.setTime(date1.getTime()+(second*1000));
                registerEvent.setTime(date1);
                userInfo.setUserInfo(map);
                date2.setTime(date1.getTime()+10000);
                loginEvent.setTime(date2);
                System.out.println((userId-1)+" register at::"+date1);
                System.out.println((userId-1)+" login at::"+date2);
                registerEvents.add(registerEvent);
                userInfos.add(userInfo);
                loginEvents.add(loginEvent);
                for(int i=0;i<userId-1;i++){
                    if(Math.random()>0.5){
                        LoginEvent loginEvent1 = new LoginEvent();
                        loginEvent1.setId(new ObjectId());
                        loginEvent1.setClientId(clientId);
                        loginEvent1.setUserId(i);
                        Date randomDate = new Date();
                        randomDate.setTime(startTime.getTime()+(int)((Math.random()*10)*8640*1000));
                        loginEvent1.setTime(randomDate);
                        System.out.println(i+" login at:"+randomDate);
                        loginEvents.add(loginEvent1);
                    }
                }
            }
            startTime.setTime(startTime.getTime()+(86400*1000));
            mongoTemplate.insertAll(registerEvents);
            mongoTemplate.insertAll(loginEvents);
            mongoTemplate.insertAll(userInfos);
            registerEvents.clear();
            loginEvents.clear();
            userInfos.clear();
        }

    }
    @Test
    public void addPageView() throws ParseException {
        String clientId = "123456789";
        String user = "user";
        int userId = 0;
        List<PageView> pageViews = new LinkedList<>();
        String time = "2019.1.1 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        Date startTime = sdf.parse(time);
        for(int j=0;j<120;j++) {
            int randomInt = (int) (Math.random() * 10);//0~9
            for(int i=0;i<100;i++){
                PageView pageView1 = new PageView();
                PageView pageView2 = new PageView();
                if(Math.random()>0.5){
                    pageView1.setUserId(i);
                    pageView1.setPageUrl("page"+((int)(Math.random() * 10)+1));
                    pageView1.setClientId(clientId);
                    pageView1.setTime(new Date(startTime.getTime()));
                    int between = (int)(Math.random() * 1000);
                    pageView1.setEndTime(new Date(startTime.getTime()+between*1000));
                    pageView2.setClientId(clientId);
                    pageView2.setUserId(i);
                    pageView2.setPageUrl("page"+((int)(Math.random() * 10)+1));
                    pageView2.setTime(new Date(startTime.getTime()+between*1000+1000));
                    pageView2.setEndTime(new Date(startTime.getTime()+between*1000+1000+(int)(Math.random() * 1000)));
                    pageViews.add(pageView1);
                    System.out.println(pageView1.toString());
                    pageViews.add(pageView2);
                    System.out.println(pageView2.toString());
                }

            }
            startTime.setTime(startTime.getTime()+(86400*1000));
            //mongoTemplate.insertAll(pageViews);
            pageViews.clear();
        }
    }

    @Test
    public void addComponent() throws ParseException {
        String clientId = "123456789";
        String user = "user";
        int userId = 0;
        List<Component> components = new LinkedList<>();
        String time = "2019.1.1 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        Date startTime = sdf.parse(time);
        for(int j=0;j<120;j++) {
            int randomInt = (int) (Math.random() * 10);//0~9
            for(int i=0;i<100;i++){
                Component component1 = new Component();
                Component component2 = new Component();
                if(Math.random()>0.5){
                    component1.setClientId(clientId);
                    component1.setUserId(i);
                    component1.setPageTitle("page"+((int)(Math.random() * 10)+1));
                    component1.setComponentType("button");
                    component1.setComponentName("button"+((int)(Math.random() * 10)+1));
                    component1.setTime(new Date(startTime.getTime()+10000));
                    components.add(component1);
                    System.out.println(component1);
                }
                if(Math.random()>0.5){
                    component2.setClientId(clientId);
                    component2.setUserId(i);
                    component2.setPageTitle("page"+((int)(Math.random() * 10)+1));
                    component2.setComponentType("a");
                    component2.setComponentName("a"+((int)(Math.random() * 10)+1));
                    component2.setTime(new Date(startTime.getTime()+10000));
                    components.add(component2);
                    System.out.println(component2);
                }

            }
            startTime.setTime(startTime.getTime()+(86400*1000));
            //mongoTemplate.insertAll(components);
            components.clear();
        }
    }

    @Test
    public void addComponentId() throws ParseException {
        String clientId = "123456789";
        String user = "user";
        int userId = 0;
        List<ComponentId> componentIds = new LinkedList<>();
        String time = "2019.1.1 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        Date startTime = sdf.parse(time);
        for(int j=0;j<120;j++) {
            int randomInt = (int) (Math.random() * 10);//0~9
            for(int i=0;i<100;i++){
                ComponentId component1 = new ComponentId();
                ComponentId component2 = new ComponentId();
                if(Math.random()>0.4){
                    component1.setClientId(clientId);
                    component1.setUserId(i);
                    component1.setPageTitle("page"+((int)(Math.random() * 10)+1));
                    component1.setComponentId("id"+((int)(Math.random() * 10)+1));
                    component1.setComponentName("button"+((int)(Math.random() * 10)+1));
                    component1.setTime(new Date(startTime.getTime()+10000));
                    componentIds.add(component1);
                    System.out.println(component1);
                }
                if(Math.random()>0.4){
                    component2.setClientId(clientId);
                    component2.setUserId(i);
                    component2.setPageTitle("page"+((int)(Math.random() * 10)+1));
                    component2.setComponentId("id"+((int)(Math.random() * 10)+1));
                    component2.setComponentName("a"+((int)(Math.random() * 10)+1));
                    component2.setTime(new Date(startTime.getTime()+10000));
                    componentIds.add(component2);
                    System.out.println(component2);
                }

            }
            startTime.setTime(startTime.getTime()+(86400*1000));
            mongoTemplate.insertAll(componentIds);
            componentIds.clear();
        }
    }

    @Test
    public void addCustomEvent() throws ParseException {
        String clientId = "123456789";
        String user = "user";
        int userId = 0;
        List<CustomEvent> customEvents = new LinkedList<>();
        String time = "2019.1.1 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        Date startTime = sdf.parse(time);
        for(int j=0;j<120;j++) {
            int randomInt = (int) (Math.random() * 10);//0~9
            for(int i=0;i<100;i++){
                CustomEvent customEvent1 = new CustomEvent();
                CustomEvent customEvent2 = new CustomEvent();
                if(Math.random()>0.4){
                    customEvent1.setClientId(clientId);
                    customEvent1.setUserId(i);
                    customEvent1.setCustomEventName("event"+((int)(Math.random() * 10)+1));
                    customEvent1.setTime(new Date(startTime.getTime()+10000));
                    Map<String,String> map = new HashMap<>();
                    map.put("attr1","value"+((int)(Math.random() * 10)+1));
                    map.put("attr2","value"+((int)(Math.random() * 10)+1));
                    customEvent1.setEventInfo(map);
                    customEvents.add(customEvent1);
                }
                if(Math.random()>0.4){
                    customEvent2.setClientId(clientId);
                    customEvent2.setUserId(i);
                    customEvent2.setCustomEventName("event"+((int)(Math.random() * 10)+1));
                    customEvent2.setTime(new Date(startTime.getTime()+10000));
                    Map<String,String> map = new HashMap<>();
                    map.put("attr1","value"+((int)(Math.random() * 10)+1));
                    map.put("attr2","value"+((int)(Math.random() * 10)+1));
                    customEvent2.setEventInfo(map);
                    customEvents.add(customEvent2);
                }
            }
            startTime.setTime(startTime.getTime()+(86400*1000));
            //mongoTemplate.insertAll(customEvents);
            customEvents.clear();
        }
    }
    @Test
    public void addCustomEventName(){
        String clientId = "123456789";
        for(int i=1;i<=10;i++){
            CustomEventName customEventName = new CustomEventName();
            customEventName.setClientId(clientId);
            customEventName.setCustomEventName("event"+i);
            //mongoTemplate.insert(customEventName);
        }
    }

    @Test
    public void checkLoginEvent() throws ParseException {
        String startStr = "2019.3.9 00:00:00";
        String endStr = "2019.3.20 23:59:59";
        String TIME = "2019.1.1 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        List<PageView> pageViews = pageViewDao.findByClientIdIsAndTimeBetween("123456789",sdf.parse(startStr),sdf.parse(endStr));
        for(PageView pageView:pageViews){
            System.out.print(pageView.getTime()+"--");
            System.out.println(pageView.getEndTime());
        }
    }
}



