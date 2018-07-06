package com.androidcoban.flicks.Api;

import com.androidcoban.flicks.Models.PlayNowMovies;
import com.androidcoban.flicks.Models.TrailerList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiSources {
     String Api_Key = "7442a5489ab52ffcad8a627ad31145ec";
     String Base_Url = "https://api.themoviedb.org/";
     String Base_image_Url = "http://image.tmdb.org/t/p"; //https://developers.themoviedb.org/3/getting-started/images
     String Poster_Size = "/w300";
     String Backdrop_Size = "/w500";
     double Vote_Tb = 5.0;

    @GET("3/movie/now_playing?")
    Call<PlayNowMovies> getPlayNowMovies(@Query("api_key") String aip_key);
    @GET("3/movie/{id}/trailers")
    Call<TrailerList> getTrailer(@Path("id") int id , @Query("api_key") String api_key);
}