package com.example.hybridapp_example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sdk.wisetracker.base.tracker.common.log.WiseLog;
import com.sdk.wisetracker.new_dot.open.DOT;

public class WebViewActivity extends Activity {

    private final String TAG = "WebViewActivity";
    private WebView webView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_activity);
        setWebView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setWebView() {

        webView = findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if( view.getProgress() == 100 ){
                    DOT.injectJavascript(view);
                    WiseLog.d(" onPageFinished(V2) - Completed  "+ view.getProgress() );
                }
                else{
                    WiseLog.d(" onPageFinished(V2) - Loading "+ view.getProgress() );
                }
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setTextZoom(100);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);


        // chrome://inspect 사용을 위해서 개발 환경에서 설정 , 배포시는 삭제
        webView.setWebContentsDebuggingEnabled(true);

        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webView.loadUrl("file:///android_asset/www/main.html");


        DOT.setWebView(webView);

    }

}
