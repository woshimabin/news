package com.itemp.news.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.itemp.news.Adapter.testAdapter;
import com.itemp.news.Been.NewsBeen;
import com.itemp.news.Item_click_Activity;
import com.itemp.news.R;
import com.itemp.news.Util.HttpUtil;
import com.itemp.news.Util.JsonUtil;
import com.itemp.news.Util.ThreadUtil;
import com.itemp.news.Util.getUrl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2016/11/7.
 */
@SuppressLint("ValidFragment")
public class testFragment extends Fragment{
    private static final String TAG = "test1";
    @BindView(R.id.rlv)
    RecyclerView rlv;
    @BindView(R.id.srf)
    SwipeRefreshLayout srf;
    @BindView(R.id.frag_tv)
    TextView fragTv;
    @BindView(R.id.frag_Prob)
    ProgressBar fragProb;
    @BindView(R.id.tv_loding_start)
    TextView tvLodingStart;
    @BindView(R.id.tv_loding_end)
    TextView tvLodingEnd;
    private View view;
    private NewsBeen newsBeen;
    private String TITLE_NAME = "";
    private static int URL_PAGE = 1;
    private List<NewsBeen.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentlist=new ArrayList<>();
    private List<NewsBeen.ShowapiResBodyBean.PagebeanBean.ContentlistBean> newContentList = new ArrayList<>();
    private final int MSG_NEWS_RESULT_GOT=10;
    @SuppressLint("ValidFragment")
    public testFragment(String TITLE_NAME) {
        this.TITLE_NAME = TITLE_NAME;
    }


    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_NEWS_RESULT_GOT:
                    srf.setRefreshing(false);//标记刷新结束
                    tvLodingStart.setVisibility(View.GONE);
                    tvLodingEnd.setVisibility(View.GONE);
                    //处理json为空的情况
                    if (msg.obj != null) {
                        String json = ((String) msg.obj);
                        Log.d(TAG, "handleMessage: json=" + json);

                        newContentList.clear();

                        newsBeen = null;


                        Log.d(TAG, "handleMessage: newsBeen=" + newsBeen);

                        while (newsBeen == null) {
                            newsBeen = JsonUtil.parseNews(json);

                            Log.e(TAG, "handleMessage:json " + json);
                            contentlist = newsBeen.getShowapi_res_body().getPagebean().getContentlist();
                        }
                        Log.e(TAG, "handleMessage: contentlist" + contentlist);


                        for (int i = 0; i < contentlist.size(); i++) {
                            if (contentlist.get(i).getImageurls().size() > 0) {
                                newContentList.add(contentlist.get(i));
                            }
                            if (newContentList.size() >= 20 * URL_PAGE) {
                                break;
                            }
                        }

                        Log.e(TAG, "handleMessage:newContentList " + newContentList);

                        if (newContentList.size() > 0) {
                            adapter.setContentlist(newContentList);

                            adapter.notifyDataSetChanged();

                           adapter.setOnItemClickListener(new testAdapter.OnItemClickListener() {
                               @Override
                               public void onItemClick(int position) {
                                   NewsBeen.ShowapiResBodyBean.PagebeanBean.ContentlistBean contentlistBean = newContentList.get(position);
                                   String link = contentlistBean.getLink();
                                   Intent intent = new Intent(getActivity(), Item_click_Activity.class);
                                   Bundle bundle = new Bundle();
                                   bundle.putString("link", link);
                                   intent.putExtras(bundle);
                                   getActivity().startActivity(intent);

                                   Log.d(TAG, "onItemClick:");
                               }
                           });
                            fragTv.setVisibility(View.GONE);
                            fragProb.setVisibility(View.GONE);

                        } else {
                            Toast.makeText(getActivity(), "数据异常，请检查网络", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), "正在加载数据", Toast.LENGTH_SHORT).show();
                        break;
                    }



            }

        }
    };
    private testAdapter adapter;
    private int lastVisibleItem;
    private LinearLayoutManager manager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment, container, false);
            ButterKnife.bind(this, view);
        }


        initData(TITLE_NAME, URL_PAGE);
        manager = new LinearLayoutManager(getActivity());
        rlv.setLayoutManager(manager);
        rlv.setItemAnimator(new DefaultItemAnimator());
        adapter = new testAdapter(getActivity(), newContentList);
        rlv.setAdapter(adapter);
        reflash();

        return view;
    }

    private void reflash() {
        srf.setColorSchemeColors(getResources().getColor(R.color.blue));
        srf.setProgressViewOffset(false,200,450);
        srf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                tvLodingStart.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        URL_PAGE = URL_PAGE + 1;
                        initData(TITLE_NAME, URL_PAGE);//刷新数据和适配器
                    }
                }, 3000);
            }
        });


        rlv.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE  && lastVisibleItem + 1 == adapter.getItemCount()) {
                    srf.setRefreshing(true);
                    tvLodingEnd.setVisibility(View.VISIBLE);

                    URL_PAGE = URL_PAGE + 1;
                    initData(TITLE_NAME, URL_PAGE);//刷新数据和适配器
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = manager.findLastVisibleItemPosition();
            }
        });
    }

    private void initData(final String name, final int page) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                final String url = getUrl.getUrl(name, page, 50);
                Log.e(TAG, "run: " + name + "/" + page);
                HttpUtil.okHttpAsyncGet(getActivity(),url,handler,MSG_NEWS_RESULT_GOT);
            }
        });
    }
}
