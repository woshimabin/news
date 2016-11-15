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
import com.itemp.news.Adapter.EasyAdapter;
import com.itemp.news.Been.easyBean;
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

public class EasyActivity extends AppCompatActivity {


    private static final String TAG = "testEasy";
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
    @BindView(R.id.ptrflv)
    PullToRefreshListView ptrflv;
    private Intent intent;
    private final int MSG_EASY_RESULT_GOT=20;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_EASY_RESULT_GOT:
                    if (msg.obj != null) {
                        String json = ((String) msg.obj);
                        Log.e(TAG, "handleMessage: json" + json);
                        easyBean easy = JsonUtil.parseEasyBean(json);
                        contentlist = easy.getShowapi_res_body().getPagebean().getContentlist();
                        Log.e(TAG, "handleMessage:contentlist " + contentlist);
                        easyAdapter.setContentlist(contentlist);
                        easyAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(EasyActivity.this, "数据异常，请检查网络", Toast.LENGTH_SHORT).show();
                    }
                    ptrflv.onRefreshComplete();
                    break;
            }
        }
    };
    private List<easyBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentlist = new ArrayList<>();
    private EasyAdapter easyAdapter;
    private int EASY_PAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy);
        ButterKnife.bind(this);


        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rg_main_rb1:
                        intent = new Intent(EasyActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.rg_main_rb3:
                        intent = new Intent(EasyActivity.this, TopicActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.rg_main_rb4:
                        intent = new Intent(EasyActivity.this, MyActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });

        initData(EASY_PAGE);
        Log.e(TAG, "onCreate: contentlist" + contentlist);
        easyAdapter = new EasyAdapter(this, contentlist);
        ptrflv.setAdapter(easyAdapter);

        ptrflv.setMode(PullToRefreshBase.Mode.BOTH);

        ILoadingLayout loadingLayoutProxy = ptrflv.getLoadingLayoutProxy();
        loadingLayoutProxy.setReleaseLabel("刷新数据");
        loadingLayoutProxy.setRefreshingLabel("正在加载数据...");
        loadingLayoutProxy.setLastUpdatedLabel("上次更新时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));

        ptrflv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                ThreadUtil.execute(new Runnable() {
                    @Override
                    public void run() {
                        EASY_PAGE+=1;
                        initData(EASY_PAGE);

                    }
                });


            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                ThreadUtil.execute(new Runnable() {
                    @Override
                    public void run() {
                        EASY_PAGE+=1;
                        initData(EASY_PAGE);
                    }
                });
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        rgMainRb2.setChecked(true);
    }

    private void initData(final int page) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                final String url = getUrl.getEasyUrl(page);
                HttpUtil.okHttpAsyncGet(EasyActivity.this, url, handler,MSG_EASY_RESULT_GOT);
            }
        });
    }
}
