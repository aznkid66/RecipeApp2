package com.example.patrick.recipeapp2;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebView;

public class WebpageActivity extends ActionBarActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webpage);

        WebView myWebView = (WebView) findViewById(R.id.activity_webpage);
        myWebView.loadUrl(getIntent().getStringExtra("URL"));

    }


}
