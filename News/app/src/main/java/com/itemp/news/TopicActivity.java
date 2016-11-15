package com.itemp.news;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.itemp.news.Adapter.VideoAdapter;
import com.itemp.news.Been.VideoBean;
import com.itemp.news.Util.HttpUtil;
import com.itemp.news.Util.JsonUtil;
import com.itemp.news.Util.ThreadUtil;
import com.itemp.news.Util.getUrl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopicActivity extends AppCompatActivity {

    private static final String TAG ="TopicActivity" ;
    @BindView(R.id.vp)
    PullToRefreshListView vp;
    @BindView(R.id.rg_main_rb1)
    RadioButton rgMainRb1;
    @BindView(R.id.rg_main_rb2)
    RadioButton rgMainRb2;
    @BindView(R.id.rg_main_rb3)
    RadioButton rgMainRb3;
    @BindView(R.id.rg_main_rb4)
    RadioButton rgMainRb4;
    @BindView(R.id.rg_main)
    RadioGroup rgMain;
    private Intent intent;
    private VideoAdapter adapter;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_VIDEO_RESULT_GOT :
                    if (msg.obj != null) {
                        String json = ((String) msg.obj);
                        VideoBean videoBean = JsonUtil.parseVideoBean(json);
                        contentlist = videoBean.getShowapi_res_body().getPagebean().getContentlist();
                        Log.e(TAG, "handleMessage: contentlist"+contentlist);
                        adapter.setContentlist(contentlist);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(TopicActivity.this, "数据异常，请检查网络", Toast.LENGTH_SHORT).show();
                    }
                    vp.onRefreshComplete();
                    break;
            }
        }
    };
    private int VIDEO_PAGE = 1;;
    private final int MSG_VIDEO_RESULT_GOT=30;
    private List<VideoBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentlist=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        ButterKnife.bind(this);

        RadioGroupClick();

        initDate(VIDEO_PAGE);


        adapter = new VideoAdapter(this,contentlist);
        vp.setAdapter(adapter);


        vp.setMode(PullToRefreshBase.Mode.BOTH);

        ILoadingLayout loadingLayoutProxy = vp.getLoadingLayoutProxy();
        loadingLayoutProxy.setReleaseLabel("刷新数据");
        loadingLayoutProxy.setRefreshingLabel("正在加载数据...");
        loadingLayoutProxy.setLastUpdatedLabel("上次更新时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));

        vp.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                ThreadUtil.execute(new Runnable() {
                    @Override
                    public void run() {
                        VIDEO_PAGE+=1;
                        initDate(VIDEO_PAGE);

                    }
                });


            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                ThreadUtil.execute(new Runnable() {
                    @Override
                    public void run() {
                        VIDEO_PAGE+=1;
                        initDate(VIDEO_PAGE);
                    }
                });
            }
        });



    }

    private void initDate(final int page) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                final String url = getUrl.getVideoUrl(page);
                HttpUtil.okHttpAsyncGet(TopicActivity.this, url, handler, MSG_VIDEO_RESULT_GOT);
            }
        });
    }

    private void RadioGroupClick() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rg_main_rb1:
                        intent = new Intent(TopicActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.rg_main_rb2:
                        intent = new Intent(TopicActivity.this, EasyActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.rg_main_rb4:
                        intent = new Intent(TopicActivity.this, MyActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        rgMainRb3.setChecked(true);
    }
}
