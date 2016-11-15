package com.itemp.news;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Item_click_Activity extends AppCompatActivity {


    @BindView(R.id.tvTestWeb)
    WebView tvTestWeb;
    @BindView(R.id.progress)
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_click_);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String link = bundle.getString("link");

        initWebViewSettings();

        tvTestWeb.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progress.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progress.setVisibility(View.GONE);
            }

        });

        tvTestWeb.loadUrl(link);
    }

    private void initWebViewSettings() {
        WebSettings webSettings = tvTestWeb.getSettings();
        webSettings.setAllowFileAccess(true);
        tvTestWeb.getSettings().setUseWideViewPort(true); //将图片调整到适合webview的大小
        tvTestWeb.getSettings().setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        tvTestWeb.getSettings().setPluginState(WebSettings.PluginState.OFF);
        tvTestWeb.getSettings().setJavaScriptEnabled(true);
        tvTestWeb.getSettings().setSupportZoom(true);
        tvTestWeb.getSettings().setBuiltInZoomControls(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && tvTestWeb.canGoBack()) {
            finish();
            return true;
        }
        finish();
        return false;
    }

}
