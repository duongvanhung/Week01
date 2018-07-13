package com.example.hung.newyorktimes.network;

import com.example.hung.newyorktimes.utils.ArticlesFilter;

public class ArticleRequestParams {

    private int page;

    private String query;

    //private Filter filter;
    String beginDate;
    String sortOrder;
    String newsDesk;
    private ArticlesFilter filter;

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getNewsDesk() {
        return newsDesk;
    }

    public void setNewsDesk(String newsDesk) {
        this.newsDesk = newsDesk;
    }


    public ArticleRequestParams() {

    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public ArticlesFilter getFilter() {
        return filter;
    }

    public void setFilter(ArticlesFilter filter) {
        this.filter = filter;
    }
}
