package com.example.usphuong.thenewyorktimes.articles.view;

import com.example.usphuong.thenewyorktimes.models.Doc;

import java.util.List;

public interface ListArticleView {

    void showListArticle(List<Doc> docs);

    void showError();

    void showErrorNetwork();

    }
