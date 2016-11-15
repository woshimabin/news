package com.itemp.news.Util;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by admin on 2016/10/26.
 */
public class HttpUtil {



    public static final int MSG_TEXT_RESULT_GOT = 10;
    private static final String TAG ="test";
    private static OkHttpClient okHttpClient;
    private static File externalCacheDir;



    private static String getStringByOkhttp(String url) {
        String json = "";
        OkHttpClient okHttpClient = new OkHttpClient();//1、创建OkHttpClient
        Request request = new Request.Builder()//2、通过构造器构造Request
                .url(url)//配置URL
                .tag("tag")//为request设置标签，将来可以通过标签找到请求，并取消之
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();//3、执行Request

            //4、解析Response
            if (response.isSuccessful()) {
                json = response.body().string();//拿到字符流，还可以是bytes(),byteStream()
            } else {
                json = "response not successful";
            }
        } catch (IOException e) {
            e.printStackTrace();
            json = "IOException";
        }

        return json;
    }



    public static void okHttpAsyncGet(Context context, final String url, final Handler handler, final int what) {

        if (okHttpClient == null) {
            initOkHttpClient(context);
        }

        final Request request = new Request.Builder()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String json = null;
                handlerSendMsg(json, handler,what);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: " + Thread.currentThread().getName());
//                if (response.cacheResponse() != null) {
//                    String json = response.cacheResponse().body().string();
//                    handlerSendMsg(json, handler);//有缓存则使用缓存
//                }

                if (response.isSuccessful()) {
                    String json = response.body().string();
                    handlerSendMsg(json, handler,what);
                } else {
                    String json = null;
                    handlerSendMsg(json, handler,what);
                }
            }
        });
    }

    private static void handlerSendMsg(String result, Handler handler,int what) {
        Message msg = handler.obtainMessage();
        msg.obj = result;
        msg.what = what;
        handler.sendMessage(msg);
    }

    private static void initOkHttpClient(Context context) {
        externalCacheDir = context.getExternalCacheDir();
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .cache(new Cache(externalCacheDir, 20 * 1024 * 1024))
                .build();
    }


}
