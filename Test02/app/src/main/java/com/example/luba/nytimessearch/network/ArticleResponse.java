package com.example.luba.nytimessearch.network;

import com.example.luba.nytimessearch.models.Article;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by luba on 9/23/17.
 */

public class ArticleResponse {

    @SerializedName("docs")
    ArrayList<Article> articles;

    JSONArray articleJsonResults;

    public ArticleResponse (){

    }

    public ArrayList<Article> getArticles() {
        return articles;
    }
    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

    public JSONArray getArticleJsonResults(){
        return articleJsonResults;
    }

    public void setArticleJsonResults(JSONArray articleJsonResults) {
        this.articleJsonResults = articleJsonResults;
    }


}
