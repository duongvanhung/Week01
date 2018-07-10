package com.example.usphuong.thenewyorktimes.articles.respository;


import com.example.usphuong.thenewyorktimes.models.SearchFilters;

public interface ArticleRepository {

    //void getData(DataListener listener,Integer page, String order, String query, String beginDate, String newsDesk);

    void getData(DataListener dataListener, Integer page, SearchFilters filters);

}
