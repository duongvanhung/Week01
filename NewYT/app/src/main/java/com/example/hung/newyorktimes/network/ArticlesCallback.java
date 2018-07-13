package com.example.hung.newyorktimes.network;



public interface ArticlesCallback {
    void onSuccess(ArticleResponse response);

    void onError(Error error);
}