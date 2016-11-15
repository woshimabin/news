package com.itemp.news.Util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.itemp.news.Been.NewsBeen;
import com.itemp.news.Been.VideoBean;
import com.itemp.news.Been.easyBean;

/**
 * Created by admin on 2016/10/26.
 */
public class JsonUtil {

    public static NewsBeen parseNews(String json) {
        NewsBeen news=null;
        try {
         news=  new Gson().fromJson(json,NewsBeen.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return news;
    }
    public static easyBean parseEasyBean(String json) {
        easyBean news=null;
        try {
         news=  new Gson().fromJson(json,easyBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return news;
    }
    public static VideoBean parseVideoBean(String json) {
        VideoBean news=null;
        try {
         news=  new Gson().fromJson(json,VideoBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return news;
    }

}
