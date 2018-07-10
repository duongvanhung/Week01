package com.example.usphuong.thenewyorktimes.articles.presenter;

import android.content.Context;

import com.example.usphuong.thenewyorktimes.articles.respository.ArticleRepository;
import com.example.usphuong.thenewyorktimes.articles.respository.DataListener;
import com.example.usphuong.thenewyorktimes.articles.view.ListArticleActivity;
import com.example.usphuong.thenewyorktimes.articles.view.ListArticleView;
import com.example.usphuong.thenewyorktimes.models.Doc;
import com.example.usphuong.thenewyorktimes.models.SearchFilters;
import com.example.usphuong.thenewyorktimes.utils.NetWorkUtil;

import java.util.List;

public class ListArticlePresenterImpl implements ListArticlePresenter, DataListener{

    private ListArticleView mView;
    private ArticleRepository articleRepository;

    public ListArticlePresenterImpl(ListArticleView mView, ArticleRepository articleRepository){
        this.mView = mView;
        this.articleRepository = articleRepository;
    }
    @Override
    public void onResponse(List<Doc> docs) {

        mView.showListArticle(docs);
    }

    @Override
    public void onError(String error) {
        mView.showError();
    }


    @Override
    public void searchArticles(Integer page, SearchFilters searchFilters) {
        if (NetWorkUtil.isNetworkAvailable() || NetWorkUtil.isOnline()) {
            articleRepository.getData(this, page, searchFilters);
        }
        else mView.showErrorNetwork();
    }
}
