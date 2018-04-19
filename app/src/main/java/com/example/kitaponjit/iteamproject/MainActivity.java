package com.example.kitaponjit.iteamproject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    String host;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        host = getIntent().getStringExtra("host");
        PrefsUtils.setHost(this, host);
        webView = findViewById(R.id.webView);
        loadWebView();
    }

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    private void loadWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.addJavascriptInterface(new webAppInterface(this), "webAppInterface");
        CookieManager cm = CookieManager.getInstance();
        CookieSyncManager.createInstance(this);
        cm.setAcceptCookie(true);

        final ProgressDialog pm = new ProgressDialog(MainActivity.this);
        //pm.setMessage("Loading..");
        pm.setCancelable(false);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                view.loadDataWithBaseURL("", "<body style='background-color:#FFF'><h2 style='color:#000'> Error : " + errorCode + " " + description + "</h2><button onclick=\"webAppInterface.goBack()\">Back set IP</button></body>", "text/html", "UTF-8", "");
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                Button btn = new Button(MainActivity.this);
                btn.setLayoutParams(params);
                btn.setGravity(Gravity.CENTER_HORIZONTAL);
                btn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 32);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                CookieSyncManager.getInstance().sync();
            }
        });

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                pm.setMessage("Loading.." + newProgress + "%");
                pm.show();
                if(newProgress == 100 && pm.isShowing() ){
                    //getSupportActionBar().setTitle("iTeam");
                    pm.dismiss();
                }
            }
        });

        host = PrefsUtils.getHost(this);
        if(host.length() == 0){
            webView.loadDataWithBaseURL("","not url.." , "text/html", "UTF-8", "");
        }

        if(!isOnline()){
            webView.loadDataWithBaseURL("","offline.." , "text/html", "UTF-8", "");
        }
        else{
            webView.loadDataWithBaseURL("","Loading.." , "text/html", "UTF-8", "");
            webView.loadUrl(host);
        }



    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private class webAppInterface {
        Context isContext;
        public webAppInterface(Context context) {
            isContext = context;
        }

        @JavascriptInterface
        public void goBack(){
            PrefsUtils.clearHost(MainActivity.this);
            startActivity(new Intent(MainActivity.this, ConfigActivity.class));
            finish();
        }
    }
}
