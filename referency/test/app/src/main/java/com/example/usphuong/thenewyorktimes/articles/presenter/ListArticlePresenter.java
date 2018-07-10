package com.example.usphuong.thenewyorktimes.articles.presenter;

import com.example.usphuong.thenewyorktimes.models.SearchFilters;

public interface ListArticlePresenter {

    void searchArticles(Integer page, SearchFilters searchFilters);

}