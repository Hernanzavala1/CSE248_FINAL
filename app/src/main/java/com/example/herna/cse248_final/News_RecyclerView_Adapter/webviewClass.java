package com.example.herna.cse248_final.News_RecyclerView_Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.example.herna.cse248_final.R;

public class webviewClass extends AppCompatActivity {

    private WebView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_webview);
        view = findViewById(R.id.webview);

        Intent intent = getIntent();
        String url = intent.getStringExtra("article_url");
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);




    }

}
