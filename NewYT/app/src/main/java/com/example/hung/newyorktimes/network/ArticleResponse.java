package com.example.hung.newyorktimes.network;

import com.example.hung.newyorktimes.models.Article;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

import java.util.ArrayList;


public class ArticleResponse {

    @SerializedName("docs")
    ArrayList<Article> articles;

    JSONArray articleJsonResults;

    public ArticleResponse() {

    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

    public JSONArray getArticleJsonResults() {
        return articleJsonResults;
    }

    public void setArticleJsonResults(JSONArray articleJsonResults) {
        this.articleJsonResults = articleJsonResults;
    }
}