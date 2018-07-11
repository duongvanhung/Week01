package com.example.hung.newyorktimes.utils;

import org.parceler.Parcel;

import java.util.HashSet;



@Parcel
public class ArticlesFilter{



    String beginDate;

    String sort;

    HashSet<String> newsDesks;


    public ArticlesFilter() {

    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }


    public String getSortOrder() {
        return sort;
    }

    public void setSortOrder(String sort) {
        this.sort = sort;
    }

    public HashSet<String> getNewsDesk() {
        return newsDesks;
    }

    public void setNewsDesk(HashSet<String> newsDesk) {
        this.newsDesks = newsDesk;
    }

    public String getFormattedNewsDesk() {
        StringBuilder sb = new StringBuilder();
        sb.append("news_desk:(");
        for (String value : newsDesks) {
            sb.append("\"").append(value).append("\" ");
        }
        // Remove last character
        sb.setLength(sb.length() - 1);
        sb.append(")");
        return sb.toString();
    }
}
