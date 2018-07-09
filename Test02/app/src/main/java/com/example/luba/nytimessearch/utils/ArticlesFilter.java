package com.example.luba.nytimessearch.utils;

import org.parceler.Parcel;

import java.util.HashSet;


/**
 * Created by luba on 9/24/17.
 */

@Parcel
public class ArticlesFilter{

    //public static final String NEWS_DESK_ARTS = "Arts";
    //public static final String NEWS_DESK_FASHION = "Fashion & Style";
    //public static final String NEWS_DESK_SPORTS = "Sports";

    //private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");


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
