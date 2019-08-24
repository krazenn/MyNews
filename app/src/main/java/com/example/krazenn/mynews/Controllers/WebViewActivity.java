package com.example.krazenn.mynews.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

import com.example.krazenn.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WebViewActivity extends AppCompatActivity {
    @BindView(R.id.webView)
    WebView webView;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        Intent i = getIntent();
        url = i.getStringExtra("url");
        Log.e("seconde activiter", "Position : " + url);
        webView.loadUrl(url);
    }
}
