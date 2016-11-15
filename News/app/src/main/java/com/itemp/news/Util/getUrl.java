package com.itemp.news.Util;

import java.text.SimpleDateFormat;

/**
 * Created by admin on 2016/11/1.
 */
public class getUrl {
    private static final String TAG = "test";
    private static String systemTime = getSystemTime();
//    private static int MAX_RESULT;//返回的新闻条数
    private static int APP_ID = 25281;
    private static String SYSTEM_TIME = systemTime;//得到当前时间的新闻SYSTEM_TIME = "20161025235917";
//    private static String TITLE_NAME;//新闻标题
    private static String APP_SIGN = "c25453fb117047e195f7a79891567288";
//    private static String URL_PAGE;



    public  static String getUrl(String TITLE_NAME,int URL_PAGE,int MAX_RESULT){
        String url;
        if(MAX_RESULT>=20&&MAX_RESULT<=100){
             url="https://route.showapi.com/109-35?" + "channelId=&channel" + "Name=&maxResult=" + MAX_RESULT + "&needAllList=0" + "&needContent=0" + "&needHtml=0" + "&page=" + URL_PAGE + "&showapi_appid=" + APP_ID + "&showapi_timestamp=" + SYSTEM_TIME + "&title=" + TITLE_NAME + "&showapi_sign=" + APP_SIGN;
        }else{
            url=null;
        }
        return url;
    }


    public  static String getEasyUrl(int URL_PAGE){

        String url="https://route.showapi.com/255-1?page="+URL_PAGE +"&showapi_appid=25281" +"&showapi_timestamp=20161107103322" +"&title=&type=29" +"&showapi_sign=c25453fb117047e195f7a79891567288";
        return url;
    }
    public  static String getVideoUrl(int URL_PAGE){

        String url="https://route.showapi.com/255-1?page="+URL_PAGE +"&showapi_appid=25281" +"&showapi_timestamp=20161107103322" +"&title=&type=41" +"&showapi_sign=c25453fb117047e195f7a79891567288";
        return url;
    }
    public  static String getMusicUrl(int URL_PAGE){

        String url="https://route.showapi.com/255-1?page="+URL_PAGE +"&showapi_appid=25281" +"&showapi_timestamp=20161107103322" +"&title=&type=31" +"&showapi_sign=c25453fb117047e195f7a79891567288";
        return url;
    }




    private static String getSystemTime() {
        long time = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String systemTime = format.format(time);
        return systemTime;
    }
}
