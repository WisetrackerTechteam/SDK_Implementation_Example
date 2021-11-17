package com.example.hybridapp_example;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.applinks.AppLinkData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.sdk.wisetracker.base.tracker.common.log.WiseLog;
import com.sdk.wisetracker.new_dot.open.DOT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.net.URLDecoder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // WiseTracker SDK 초기화.
        if (getIntent() == null || getIntent().getData() == null || TextUtils.isEmpty(getIntent().getData().toString())) {
            WiseLog.d("@@@@@@@@@@@@@ DOT.initialization... ");
            DOT.initialization(this);
        }
        else{
            WiseLog.d("@@@@@@@@@@@@@ DOT.setDeepLink... ");
            DOT.setDeepLink(getApplicationContext(), getIntent());
        }

        // Facebook 분석 기능 추가.
        this.getFacebookDeferredAppLinkDataForWiseTrackerSDK();
        /** Wisetracker 수정 start **/

    }

    // Facebook Deferred deeplink 수신
    private void getFacebookDeferredAppLinkDataForWiseTrackerSDK(){
        try{
            // Get Facebook AppLinkData
            AppLinkData.fetchDeferredAppLinkData(getApplicationContext(), new AppLinkData.CompletionHandler() {
                @Override
                public void onDeferredAppLinkDataFetched(AppLinkData appLinkData) {
                    if (appLinkData == null) {
                        DOT.receiveFailFacebookReferrer(1);
                        return ;
                    }
                    Bundle bundle = appLinkData.getArgumentBundle();
                    if (bundle == null) {
                        DOT.receiveFailFacebookReferrer(2);
                        return;
                    }
                    DOT.setFacebookReferrer(bundle);
                    //-------------------------------------------------------------
                    // Facebook 에서 지연된 딥링크로 전달받은 url로 화면이 자동으로 이동하도록 처리.
                    redirectScreenFromFacebookDeferredUrl(bundle);
                    //-------------------------------------------------------------
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void redirectScreenFromFacebookDeferredUrl(Bundle bundle){
        try{
            if( bundle != null ){
                //-------------------------------------------------------------
                // Facebook 에서 지연된 딥링크로 전달받은 url로 화면이 자동으로 이동하도록 처리.
                if (bundle.containsKey("target_url") || bundle.containsKey("com.facebook.platform.APPLINK_NATIVE_URL")) {
                    String parsedDeepLink = bundle.getString("target_url");
                    if (TextUtils.isEmpty(parsedDeepLink)) {
                        parsedDeepLink = bundle.getString("com.facebook.platform.APPLINK_NATIVE_URL");
                    }
                    if( parsedDeepLink.indexOf("&w_start=") > 0 ){
                        parsedDeepLink = parsedDeepLink.substring(0,parsedDeepLink.indexOf("&w_start="));
                    }
                    // parsedDeepLink 변수값을 사용하여 화면을 이동시켜줘야 합니다.
                    // example : scheme://host?url=https://your_website_url
                    if( parsedDeepLink != null ){
                        WiseLog.d("move to deeplink : "+ parsedDeepLink );
                    }
                    // ----------------------------------------------------------------------------------------------------
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        DOT.setDeepLink(getApplicationContext(), getIntent());

        // deeplink 페이지 이동 처리 구현 시작
        // .....

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}