package com.example.hung.newyorktimes.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.hung.newyorktimes.R;
import com.example.hung.newyorktimes.models.Article;

import org.parceler.Parcels;


public class ListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        WebView webView = (WebView) findViewById(R.id.tvArticles);

        final Article article = (Article) Parcels.unwrap(getIntent().getParcelableExtra("article"));
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
                view.loadUrl(article.getWebUrl());
                return true;
            }
        });
        webView.loadUrl(article.getWebUrl());

    }
}