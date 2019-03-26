package com.hisu.smart.dj.utils;

import android.util.Log;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lichee on 2019/3/22.
 */

public class ZoneUtils {
    private final static String TAG = "ZoneUtils";
    public static String  handlePublishTimeDesc(String post_modified){
        Log.i(TAG,"handlePublishTimeDesc================="+post_modified);
        if(null == post_modified || "".equals(post_modified)){
            return "";
        }
        Date curTime = new Date();
        long timeDiff = curTime.getTime() - getTime(post_modified);
        // 单位换算
        long min = 60 * 1000;
        long hour = min * 60;
        long day = hour * 24;
        long week = day * 7;
        long month =  week*4;
        long year = month*12;
        // 计算发布时间距离当前时间的周、天、时、分
        int  exceedyear = Integer.parseInt(new DecimalFormat("0").format(Math.floor(timeDiff/year)));
        int exceedmonth = Integer.parseInt(new DecimalFormat("0").format(Math.floor(timeDiff/month)));
        int exceedWeek = Integer.parseInt(new DecimalFormat("0").format(Math.floor(timeDiff/week)));
        int exceedDay = Integer.parseInt(new DecimalFormat("0").format(Math.floor(timeDiff/day)));
        int exceedHour = Integer.parseInt(new DecimalFormat("0").format(Math.floor(timeDiff/hour)));
        int exceedMin = Integer.parseInt(new DecimalFormat("0").format(Math.floor(timeDiff/min)));
        // 最后判断时间差到底是属于哪个区间，然后return
        if(exceedyear<100&&exceedyear>0){
            return exceedyear + "年前";
        }else{
            if(exceedmonth<12&&exceedmonth>0){
                return exceedmonth + "月前";
            }else{
                if(exceedWeek<4&&exceedWeek>0){
                    return exceedWeek + "星期前";
                }else{
                    if(exceedDay < 7 && exceedDay > 0){
                        return exceedDay + "天前";
                    }else {
                        if (exceedHour < 24 && exceedHour > 0) {
                            return exceedHour + "小时前";
                        } else {
                            if(exceedMin == 0){
                                return "刚刚";
                            }
                            return exceedMin + "分钟前";
                        }
                    }
                }
            }
        }
    }


    private static long getTime(String dateString){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
        }
        return date.getTime();
    }


}
