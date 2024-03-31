package com.example.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class DateCut {
    /**
     * 将一段时间切割为指定时间间隔的时间数组
     * @param dateType 时间间隔格式 0:perDay,2:perWeek,3:perMonth,4:perYear
     */
    public static String[] dateCutByDateType(Date date1, Date date2,int dateType) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
//        String  start  = sdf.format(date1);// Date-->String 开始日期String类型
//        String end = sdf.format(date2);
        int days = getDays(date1, date2);
        Date[] dates = new Date[days + 10];//此数组存放这段时间内的每天日期
        String[] Sdate = new String[days + 10];//上述时间转为String存放在此数组
        int i;
        if(dateType==0){
            for (i = 0; i <=days; i++) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date1);
                calendar.add(Calendar.DATE, i );
                dates[i] = calendar.getTime();
                Sdate[i] = sdf.format(dates[i]);// Date-->String
            }
        }//0按日查询新增人数
        else if(dateType==1){
            for (i = 0; i <=days/7+2; i++) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date1);
                calendar.add(Calendar.WEEK_OF_MONTH ,-1);
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);//将时间改为本周第一天
                calendar.add(Calendar.DATE, i*7+7);
                dates[i] = calendar.getTime();
                Sdate[i] = sdf.format(dates[i]);// Date-->String
            }
        }//1按周查询新增人数
        else  if(dateType==2){
            for (i = 0; i <=days/30+1; i++) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date1);
                calendar.add(Calendar.MONTH, -1);
                calendar.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
                calendar.add(Calendar.MONTH, i + 1);
                dates[i] = calendar.getTime();
                Sdate[i] = sdf.format(dates[i]);// Date-->String
            }
        }//2按月查询新增人数
        else{
            for (i = 0; i <=days/365+1; i++) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date1);
                calendar.add(Calendar.YEAR,-1);
                calendar.set(Calendar. DAY_OF_YEAR, 1);//将时间改为本年第一天
                calendar.add(Calendar.YEAR, i + 1);
                dates[i] = calendar.getTime();
                Sdate[i] = sdf.format(dates[i]);// Date-->String
            }
        }//3按年查询新增人数
        Sdate[i] = "\0";
        List<String> list = new ArrayList<>();
        for(String d:Sdate){
            if(d.equals("\0")){
                break;
            }else{
                list.add(d);
            }
        }
        return list.toArray(new String[0]);
    }

    public static int getDays(Date date1, Date date2) {
        int day = (int) ((date2.getTime() - date1.getTime()) / (3600 * 1000 * 24));
        return day;
    }

    /**
     * 格式化时间字符串数组（YYYY.MM.DD)
     * @param date1
     * @param date2
     * @param dateType
     * @return
     */
    public static String[] dateStringFormat(Date date1, Date date2,int dateType){
        String[] date = dateCutByDateType(date1, date2,dateType);
        for (int i=0;i<date.length;i++){
            date[i] = date[i].substring(0,11);
        }
        return  date;
    }

    public  static Date[] string2Date(String stringTime[]) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        Date date[] =new Date[stringTime.length];
        for(int i=0;i<stringTime.length;i++) {
            date[i] =sdf.parse ( stringTime[i] );
        }
        return date;
    }

    public  static Date string2Date(String stringTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        Date date=sdf.parse ( stringTime );
        return date;
    }



public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        if (date.after(begin) && date.before(end)) {
        return true;
        } else if (nowTime.compareTo(beginTime) == 0 || nowTime.compareTo(endTime) == 0) {
        return true;
        } else {
        return false;
        }
}




public static Date subTime(Date date) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
    String s = sdf.format(date);
    Date dt =  sdf.parse(s);
    return dt;
}


}
