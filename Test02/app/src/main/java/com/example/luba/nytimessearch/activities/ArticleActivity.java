package com.example.luba.nytimessearch.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.luba.nytimessearch.R;
import com.example.luba.nytimessearch.models.Article;

import org.parceler.Parcels;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        WebView webView = (WebView) findViewById(R.id.wvArticle);

        final Article article = (Article) Parcels.unwrap(getIntent().getParcelableExtra("article"));

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(article.getWebURL());
                return true;
            }
        });
        webView.loadUrl(article.getWebURL());

    }

}

