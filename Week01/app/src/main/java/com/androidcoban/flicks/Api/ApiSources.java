package com.androidcoban.flicks.Api;

import com.androidcoban.flicks.Models.PlayNowMovies;
import com.androidcoban.flicks.Models.TrailerList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiSources {
    final String Api_key = "7442a5489ab52ffcad8a627ad31145ec";
    final String Base_URL = "https://api.themoviedb.org/";
    //final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p";
    //final String POSTER_SIZE = "/w200";
   // final String BACKDROP_SIZE = "/w400";
    final double VOTE_AVERAGE = 6.0;


    @GET("3/movie/now_playing")
    Call<PlayNowMovies> getPlayNowMovies(@Query("api_key") String values);
    @GET("3/movie/{id}/trailers")
    Call<TrailerList> getTrailer(@Path("id") int id , @Query("api_key") String values);
}