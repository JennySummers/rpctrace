package com.example.demo.service;

import com.example.demo.dao.PageViewDao;
import com.example.demo.entity.PageView;
import com.example.demo.util.DateCut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.demo.util.DateCut.dateCutByDateType;


@Service
public class PageViewService {
    @Autowired
    PageViewDao pageViewDao;

    //返回一段时间内，某个客户访问过的页面名
    @Cacheable("pageViewNameAnalyse")
    public String[] pageViewNameAnalyse(String clientId, Date date1, Date date2) {
        List<PageView> pageViews = pageViewDao.findByClientIdIsAndTimeBetween(clientId, date1, date2);
        HashSet<String> pageName = new LinkedHashSet<>();
        // String[]  = new String[pageViews.size()];
        for (int i = 0; i < pageViews.size(); i++) {
            pageName.add(pageViews.get(i).getPageUrl());
        }
        return pageName.toArray(new String[0]);
    }


    //返回一段时间内，某个客户访问过的页面对应的访问次数
    @Cacheable("pageViewNumberAnalyse")
    public int[] pageViewNumberAnalyse(Date data1, Date data2, String clientId) {
        List<PageView> pageViews = pageViewDao.findByClientIdIsAndTimeBetween(clientId, data1, data2);
        HashSet<String> pageName = new LinkedHashSet<>();

        for (int i = 0; i < pageViews.size(); i++) {
            pageName.add(pageViews.get(i).getPageUrl());
        }
        String[] pageNameArray = new String[pageName.size()];
        int i = 0;
        for (String pn : pageName) {
            pageNameArray[i++] = pn;
        }

        int pageCount[] = new int[pageName.size()];
        for (i = 0; i < pageCount.length; i++) {
            pageCount[i] = 0;
        }
        for (i = 0; i < pageName.size(); i++) {
            //pageCount[i]=pageViewDao.countPageViewByPageUrlIsAndTimeBetween(pageNameArray[i],data1,data2);
            for (int j = 0; j < pageViews.size(); j++) {
                if (pageNameArray[i].equals(pageViews.get(j).getPageUrl()))
                    pageCount[i]++;
            }



        }
        return pageCount;

    }


    //按天，日，周，年返回一段时间指定页面的访问次数
    @Cacheable("pageViewNumberAnalyseAppoint")
    public int[] pageViewNumberAnalyseAppoint(String clientId, String pvName, Date date1, Date date2, int dateType) throws ParseException {
        DateCut dateCut = new DateCut();
        String[] strings = dateCut.dateCutByDateType(date1, date2, dateType);
        Date[] datesLine = dateCut.string2Date(strings);


        List<PageView> pageViews = pageViewDao.findByClientIdIsAndTimeBetween(clientId, date1, date2);
        HashSet<String> pageName = new LinkedHashSet<>();

        for (int i = 0; i < pageViews.size(); i++) {
            pageName.add(pageViews.get(i).getPageUrl());
        }
        String[] pageNameArray = new String[pageName.size()];
        int i = 0;
        for (String pn : pageName) {
            pageNameArray[i++] = pn;
        }

        int pageCount[] = new int[datesLine.length];
        for (i = 0; i < pageCount.length; i++) {
            pageCount[i] = 0;
        }
        for (i = 0; i < datesLine.length; i++) {
            for (PageView pv : pageViews) {


                if (pvName.equals(pv.getPageUrl())) {
                    Calendar pvCal = Calendar.getInstance();
                    pvCal.setTime(datesLine[i]);

                    if (dateType == 0) {
                        if (dateCut.subTime(pv.getTime()).compareTo(dateCut.subTime(datesLine[i])) == 0) {
                            //System.out.println(dateCut.subTime(pv.getTime())+"   "+pv.getTime()+"      "+dateCut.subTime(datesLine[i])+"   "+datesLine[i]);
                            pageCount[i]++;
                        }
                    } else if (dateType == 1) {
                        pvCal.add(Calendar.DAY_OF_YEAR, 7);//日期加7天
                        Date wDate = pvCal.getTime();
                        if (dateCut.belongCalendar(pv.getTime(), datesLine[i], wDate)) {
                            pageCount[i]++;
                        }
                    } else if (dateType == 2) {

                        pvCal.add(Calendar.MONTH, 1);
                        Date mDate = pvCal.getTime();
                        if (dateCut.belongCalendar(pv.getTime(), datesLine[i], mDate)) {

                            pageCount[i]++;
                        }
                    } else {
                        pvCal.add(Calendar.YEAR, 1);
                        Date nDate = pvCal.getTime();
                        if (dateCut.belongCalendar(pv.getTime(), datesLine[i], nDate)) {

                            pageCount[i]++;
                        }
                    }

                }
            }

        }
        for(i=0;i<datesLine.length;i++)
        {
            //System.out.print(pageCount[i]+" ");
            //System.out.print(datesLine[i]+" ");
        }
        return pageCount;
    }




    //按天，日，周，年返回一段时间用户访问数
    @Cacheable("pageViewUserAnalyseAppoint")
    public int[] pageViewUserAnalyseAppoint(String clientId, Date date1, Date date2, int dateType,String page) throws ParseException {
        DateCut dateCut = new DateCut();
        String[] strings = dateCut.dateCutByDateType(date1, date2, dateType);
        Date[] datesLine = dateCut.string2Date(strings);


        List<PageView> pageViews = pageViewDao.findByClientIdIsAndTimeBetweenAndPageUrlIs(clientId, date1, date2,page);
        HashSet<Integer> pageUsersD = new LinkedHashSet<>();
        HashSet<Integer> pageUsersW = new LinkedHashSet<>();
        HashSet<Integer> pageUsersM = new LinkedHashSet<>();
        HashSet<Integer> pageUsersN = new LinkedHashSet<>();

        int pageCount[] = new int[datesLine.length];
        int i = 0;
        for (i = 0; i < pageCount.length; i++) {
            pageCount[i] = 0;
        }
        for (i = 0; i < datesLine.length; i++) {


            for (PageView pv : pageViews) {

                Calendar pvCal = Calendar.getInstance();
                pvCal.setTime(datesLine[i]);
                if (dateType == 0) {
                    if (dateCut.subTime(pv.getTime()).compareTo(dateCut.subTime(datesLine[i])) == 0) {
                        pageUsersD.add(pv.getUserId());

                    }

                } else if (dateType == 1) {
                    pvCal.add(Calendar.DAY_OF_YEAR, 7);//日期加7天
                    Date wDate = pvCal.getTime();
                    if (dateCut.belongCalendar(pv.getTime(), datesLine[i], wDate)) {
                        pageUsersD.add(pv.getUserId());

                    }

                } else if (dateType == 2) {

                    pvCal.add(Calendar.MONTH, 1);
                    Date mDate = pvCal.getTime();
                    if (dateCut.belongCalendar(pv.getTime(), datesLine[i], mDate)) {
                        pageUsersD.add(pv.getUserId());

                    }

                } else {
                    pvCal.add(Calendar.YEAR, 1);
                    Date nDate = pvCal.getTime();
                    if (dateCut.belongCalendar(pv.getTime(), datesLine[i], nDate)) {
                        pageUsersD.add(pv.getUserId());

                    }

                }

            }
            if(dateType==0) {
                pageCount[i] = pageUsersD.size();
                pageUsersD.clear();
            }
            else if(dateType==1) {
                pageCount[i] = pageUsersW.size();
                pageUsersW.clear();
            }
            else if(dateType==2) {
                pageCount[i] = pageUsersM.size();
                pageUsersM.clear();
            }
            else {
                pageCount[i] = pageUsersN.size();
                pageUsersN.clear();
            }




        }
        return pageCount;
    }


    //返回一段时间内，页面对应的用户数
    @Cacheable("userNumberAnalyse")
    public int[] userNumberAnalyse(String clientId, Date date1, Date date2) {
        String[] pageName = pageViewNameAnalyse(clientId, date1, date2);
        int[] usernumber = new int[pageName.length];
        for(int k=0;k<pageName.length;k++) {
            usernumber[k]=0;
        }
        // List<PageView> pageViews = pageViewDao.findByClientIdIsAndTimeBetween(clientId, date1, date2);
        for (int i = 0; i < pageName.length; i++) {
            List<PageView> pageViews = pageViewDao.findByPageUrlIsAndClientIdIsAndTimeBetween(pageName[i], clientId, date1, date2);
            HashSet<Integer> list = new LinkedHashSet<>();
            for (int j = 0; j < pageViews.size(); j++) {
                list.add(pageViews.get(j).getUserId());
            }
            usernumber[i] = list.size();
            list.clear();
        }

        return usernumber;
    }


    //返回一段时间内，某个客户访问过的页面对应的平均页面访问时间，以秒为单位，最大为3600秒
    @Cacheable("pageViewTAnalyse")
    public int[] pageViewTAnalyse(Date data1, Date data2, String clientId,int dateType) {
        List<PageView> pageViews = pageViewDao.findByClientIdIsAndTimeBetween(clientId, data1, data2);
        HashSet<String> pageName = new LinkedHashSet<>();

        for (int i = 0; i < pageViews.size(); i++) {
            pageName.add(pageViews.get(i).getPageUrl());
        }
        String[] pageNameArray = new String[pageName.size()];
        int i = 0;
        for (String pn : pageName) {
            pageNameArray[i++] = pn;
        }

        int pageCount[] = new int[pageName.size()];
        for (i = 0; i < pageCount.length; i++) {
            pageCount[i] = 0;
        }

        int userCount[] = new int[pageName.size()];
        for (i = 0; i < userCount.length; i++) {
            userCount[i] = 0;
        }
        HashSet<Integer> list = new LinkedHashSet<>();
        for (i = 0; i < pageName.size(); i++) {

            //pageCount[i]=pageViewDao.countPageViewByPageUrlIsAndTimeBetween(pageNameArray[i],data1,data2);
            for (int j = 0; j < pageViews.size(); j++) {





                if (pageNameArray[i].equals(pageViews.get(j).getPageUrl())) {

                    list.add(pageViews.get(j).getUserId());

                    if (pageViews.get(j).getEndTime() == null) {
                        j++;
                    } else {
                        Date sTime = pageViews.get(j).getTime();
                        Date eTime = pageViews.get(j).getEndTime();
                        int sbTime = (int) ((eTime.getTime() - sTime.getTime())/1000);
                        if (sbTime > 3600) {
                            pageCount[i] = 3600;
                        } else {
                            pageCount[i] = pageCount[i]+sbTime;
                        }

                    }
                }
            }
            userCount[i] = list.size();
            list.clear();

            pageCount[i] = userCount[i]==0? 0:pageCount[i]/userCount[i];


            if(dateType==0) {
                //按天查，返回时间单位为秒
                pageCount[i] =  userCount[i];


            }
            else if(dateType==1) {
                //按周查返回时间单位为分钟
                pageCount[i] =pageCount[i]/60;

            }
            else if(dateType==2) {
                //按月查，返回时间单位为小时
                pageCount[i] = pageCount[i]/3600;

            }
            else {
                //按年查，返回时间单位为小时
                pageCount[i] = pageCount[i]/3600;

            }


        }
        return pageCount;

    }


    //返回一段时间内指定页面对应的页面平均访问时间
    //按天查，返回时间单位为秒
    //按周查，返回时间单位为分钟
    //按月查，返回时间单位小时
    //按年查，返回时间单位为小时
    @Cacheable("pageViewTAnalyseAppoint")
    public int[] pageViewTAnalyseAppoint(Date data1, Date data2,
                                         String clientId, int dateType, String pvName)
            throws ParseException {
        HashSet<Integer> pageUsersD = new LinkedHashSet<>();
        HashSet<Integer> pageUsersW = new LinkedHashSet<>();
        HashSet<Integer> pageUsersM = new LinkedHashSet<>();
        HashSet<Integer> pageUsersN = new LinkedHashSet<>();



        //分割时间
        DateCut dateCut = new DateCut();
        String[] strings = dateCut.dateCutByDateType(data1, data2, dateType);
        Date[] datesLine = dateCut.string2Date(strings);

        //获取指定时间内的所有页面
        List<PageView> pageViews = pageViewDao.findByClientIdIsAndTimeBetween(clientId, data1, data2);
        HashSet<String> pageName = new LinkedHashSet<>();
        int i = 0;
        for (i = 0; i < pageViews.size(); i++) {
            pageName.add(pageViews.get(i).getPageUrl());
        }
        String[] pageNameArray = new String[pageName.size()];
         i=0;
        for (String pn : pageName) {
            pageNameArray[i++] = pn;
        }

        //初始化页面访问时间数组
        int pageCount[] = new int[pageName.size()];
        for (i = 0; i < pageCount.length; i++) {
            pageCount[i] = 0;
        }
        for (i = 0; i < pageName.size(); i++) {
            for (int j = 0; j < pageViews.size(); j++) {

                if (pvName.equals(pageViews.get(j).getPageUrl())) {
                    Calendar pvCal = Calendar.getInstance();
                    pvCal.setTime(datesLine[i]);
                    //System.out.println(pvCal);
                    if (dateType == 0) {
                        if (dateCut.subTime(pageViews.get(j).getTime()).
                                compareTo(dateCut.subTime(datesLine[i])) == 0) {

                           //累加这段时间内访问过该页面的用户
                            pageUsersD.add(pageViews.get(j).getUserId());

                            if (pageViews.get(j).getEndTime() == null) {
                                j=j+1;
                            } else {
                                Date sTime = pageViews.get(j).getTime();
                                Date eTime = pageViews.get(j).getEndTime();
                                int sbTime = (int) ((eTime.getTime() - sTime.getTime())/1000);
                                if (sbTime > 3600) {
                                    pageCount[i] = 3600;
                                } else {
                                    pageCount[i] += sbTime;
                                }

                            }


                        }
                    } else if (dateType == 1) {
                        pvCal.add(Calendar.DAY_OF_YEAR, 7);//日期加7天
                        Date wDate = pvCal.getTime();
                        if (dateCut.belongCalendar(pageViews.get(j).getTime(),
                                datesLine[i], wDate)) {
                            //累加这段时间内访问过该页面的用户
                            pageUsersW.add(pageViews.get(j).getUserId());


                                if (pageViews.get(j).getEndTime() == null) {
                                    j=j++;
                                } else {
                                    Date sTime = pageViews.get(j).getTime();
                                    Date eTime = pageViews.get(j).getEndTime();
                                    int sbTime = (int) ((eTime.getTime() - sTime.getTime())/1000);
                                    if (sbTime > 3600) {
                                        pageCount[i] = 3600;
                                    } else {
                                        pageCount[i] = pageCount[i]+sbTime;
                                    }

                                }


                        }
                    } else if (dateType == 2) {

                        pvCal.add(Calendar.MONTH, 1);
                        Date mDate = pvCal.getTime();
                        if (dateCut.belongCalendar(pageViews.get(j).getTime(),
                                datesLine[i], mDate)) {

                            //累加这段时间内访问过该页面的用户
                            pageUsersM.add(pageViews.get(j).getUserId());

                                if (pageViews.get(j).getEndTime() == null) {
                                    j++;
                                } else {
                                    Date sTime = pageViews.get(j).getTime();
                                    Date eTime = pageViews.get(j).getEndTime();
                                    int sbTime = (int) ((eTime.getTime() - sTime.getTime())/1000);
                                    if (sbTime > 3600) {
                                        pageCount[i] = 3600;
                                    } else {
                                        pageCount[i] += sbTime;
                                    }

                                }


                        }
                    } else {
                        pvCal.add(Calendar.YEAR, 1);
                        Date nDate = pvCal.getTime();
                        if (dateCut.belongCalendar(pageViews.get(j).getTime(),
                                datesLine[i], nDate)) {

                            //累加这段时间内访问过该页面的用户
                            pageUsersN.add(pageViews.get(j).getUserId());
                                if (pageViews.get(j).getEndTime() == null) {
                                    j++;
                                } else {
                                    Date sTime = pageViews.get(j).getTime();
                                    Date eTime = pageViews.get(j).getEndTime();
                                    int sbTime = (int) ((eTime.getTime() - sTime.getTime())/1000);
                                    if (sbTime > 3600) {
                                        pageCount[i] = 3600;
                                    } else {
                                        pageCount[i] += sbTime;
                                    }

                                }


                        }


                    }


                }
            }

         //pageCount[i]=pageCount[i]/pageUsersD.size();
            if(dateType==0) {
                //按天查，返回时间单位为秒
            pageCount[i] = pageUsersD.size()==0? 0:(pageCount[i]/pageUsersD.size());

                pageUsersD.clear();
            }
            else if(dateType==1) {
                //按周查返回时间单位为分钟
                pageCount[i] =pageUsersW.size()==0? 0:  (pageCount[i]/pageUsersW.size())/60;
                pageUsersW.clear();
            }
            else if(dateType==2) {
                //按月查，返回时间单位为小时
                pageCount[i] =pageUsersM.size()==0? 0: (pageCount[i]/pageUsersM.size())/3600;
                pageUsersM.clear();
            }
            else {
                //按年查，返回时间单位为小时
                pageCount[i] =pageUsersN.size()==0? 0: (pageCount[i]/pageUsersN.size())/3600;
                pageUsersN.clear();
            }


        }
      

        return pageCount;
    }


    //计算指定页面在一段时间内跳转到其他页面的次数
    @Cacheable("pageViewJumpAnalyse")
    public int[] pageViewJumpAnalyse(Date data1, Date data2, String pvName,String clientId) {
        List<PageView> pageViews = pageViewDao.findByClientIdIsAndTimeBetween(clientId, data1, data2);
        HashSet<String> pageName = new LinkedHashSet<>();

        for (int i = 0; i < pageViews.size(); i++) {
            pageName.add(pageViews.get(i).getPageUrl());
        }
        String[] pageNameArray = new String[pageName.size()];
        int i = 0;
        for (String pn : pageName) {
            pageNameArray[i++] = pn;
        }

        int pageCount[] = new int[pageName.size()];
        for (i = 0; i < pageCount.length; i++) {
            pageCount[i] = 0;
        }


        for (i = 0; i < pageName.size(); i++) {

           for(int k=0;k<pageViews.size();k++) {
               if(pageViews.get(k).getPageUrl().equals(pvName)) {
                   for (int j = 0; j < pageViews.size(); j++) {

                       //判断与指定页面匹配的页面有没有结束时间
                       if(pageViews.get(k).getEndTime()==null) {
                           break;
                       }
                       else if(!pageViews.get(j).getPageUrl().equals(pageViews.get(k).getPageUrl())){

                           //下一个页面的开始时间减去指定页面的结束时间=跳转时间
                           //跳转时间小于等于2，则认为指定页面成功跳转到下一个页面
                           int jumpTime= (int) (pageViews.get(j).getTime().getTime()
                                   -pageViews.get(k).getEndTime().getTime())/1000;
                           if (jumpTime<=2&&jumpTime>=0)
                               pageCount[i]++;
                       }

                   }
               }
            }
        }
        return pageCount;

    }





}




