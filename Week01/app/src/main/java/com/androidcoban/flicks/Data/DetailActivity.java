package com.androidcoban.flicks.Data;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.RatingBar;
import android.widget.TextView;

import com.androidcoban.flicks.Api.ApiSources;
import com.androidcoban.flicks.Models.MoviesApi;
import com.androidcoban.flicks.Models.TrailerList;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends YouTubeBaseActivity {

    MoviesApi movie;

    @BindView(R.id.tv_detail_title) TextView tv_detail_title;
    @BindView(R.id.tv_detail_release_date) TextView tv_detail_release_date;
    @BindView(R.id.rating_bar) RatingBar rating_bar;

    @BindView(R.id.tv_overview_detail) TextView tv_overview_detail;

    @BindView(R.id.youtube_player) YouTubePlayerView youTubePlayerView;

        private static final String API_KEY_YOUTUBE = "AIzaSyAPhy7DGbkYMKjzj_q27y0UUoU1u0jbEgQ ";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        movie = getIntent().getParcelableExtra("movie");

        setDetailInformation();
        getTrailer();
    }

    /**
     * Set up detail information of movie
     */
    private void setDetailInformation() {

        tv_detail_title.setText(movie.getTitle());
        rating_bar.setRating(movie.getVoteAverage().floatValue());
        rating_bar.setIsIndicator(true);
        tv_detail_release_date.setText(movie.getReleaseDate());
        tv_overview_detail.setText(movie.getOverview());
    }

    /**
     *  The fragment by ID and then initialize the video player:
     * @param source source of video on youtube.com
     */
    public void initYouTubePlayer(final String source) {
        youTubePlayerView.initialize(API_KEY_YOUTUBE,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        if (movie.getVoteAverage() >= ApiSources.Vote_Tb)
                            youTubePlayer.loadVideo(source); // .loadVideo: Play now - .cueVideo: only load
                        else youTubePlayer.cueVideo(source);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }

    /**
     * call api and shuffle a trailer
     */
    public void getTrailer() {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ApiSources.Base_Url)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        ApiSources apiService = retrofit.create(ApiSources.class);
        Call<TrailerList> call = apiService.getTrailer(movie.getId(), ApiSources.Api_Key);
        call.enqueue(new Callback<TrailerList>() {
            @Override
            public void onResponse(@NonNull Call<TrailerList> call, @NonNull Response<TrailerList> response) {
                if (response.body() != null) {
                    int size = response.body().getYoutube().size();
                    String source = response.body().getYoutube().get(new Random().nextInt(size)).getSource();
                    initYouTubePlayer(source);
                }
            }

            @Override
            public void onFailure(@NonNull Call<TrailerList> call, Throwable t) {

            }
        });
    }



    }








