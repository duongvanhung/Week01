package com.example.hung.newyorktimes.api;

import com.example.hung.newyorktimes.models.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiSource{
    final String BASE_URL = "https://api.nytimes.com/";
    final String API_KEY = "05c01f6333f140a4ae091b7a98fcd3a8";

    @GET ("svc/search/v2/articlesearch.json")
    Call<Example> getArticles(@Query("sort") String order,
                              @Query("page") Integer page,
                              @Query("search") String search,
                              @Query("begin_date") String beginDate,
                              @Query("newsDest") String newsDest);

}