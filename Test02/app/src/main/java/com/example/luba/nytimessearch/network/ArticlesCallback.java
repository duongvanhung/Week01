package com.example.luba.nytimessearch.network;

/**
 * Created by luba on 9/20/17.
 */

public interface ArticlesCallback {
    void onSuccess(ArticleResponse response);

    void onError(Error error);
}
