package com.androidcoban.flicks.Data;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.support.v7.app.AlertDialog;


import com.androidcoban.flicks.Api.ApiSources;
import com.androidcoban.flicks.Api.CheckNetwork;
import com.androidcoban.flicks.Models.MoviesApi;
import com.androidcoban.flicks.Models.TrailerList;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends YouTubeBaseActivity {

    MoviesApi movie;

//    @BindView(R.id.img_backdrop_detail) ImageView img_backdrop_detail;

   // @BindView(R.id.img_poster_detail) ImageView img_poster_detail;

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
        Glide.with(DetailActivity.this)
                .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.backdropplaceholder))
                .load(movie.getBackdropPath());
                //.into(img_backdrop_detail);
        tv_detail_title.setText(movie.getTitle());

        Glide.with(DetailActivity.this)
                .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.posterplaceholder))
                .load(movie.getPosterPath())
                .apply(RequestOptions.bitmapTransform(
                        new RoundedCornersTransformation(111, 11, RoundedCornersTransformation.CornerType.ALL)))
               ; //.into(img_poster_detail);

        rating_bar.setRating(movie.getVoteAverage().floatValue());
        rating_bar.setIsIndicator(true);
        tv_detail_release_date.setText(movie.getReleaseDate());

        tv_overview_detail.setText(movie.getOverview());
    }

    /**
     * Initialize youtube player
     * @param source source of video on youtube.com
     */
    public void initYouTubePlayer(final String source) {
        youTubePlayerView.initialize(API_KEY_YOUTUBE,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        if (movie.getVoteAverage() >= ApiSources.VOTE_AVERAGE)
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
        showAlertDialogNoInternetConnection();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ApiSources.Base_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        ApiSources retrofitClient = retrofit.create(ApiSources.class);
        Call<TrailerList> call = retrofitClient.getTrailer(movie.getId(), ApiSources.Api_key);
        call.enqueue(new Callback<TrailerList>() {
            @Override
            public void onResponse(Call<TrailerList> call, Response<TrailerList> response) {
                if (response.body() != null) {
                    int size = response.body().getYoutube().size();
                    String source = response.body().getYoutube().get(new Random().nextInt(size)).getSource();
                    initYouTubePlayer(source);
                }
            }

            @Override
            public void onFailure(Call<TrailerList> call, Throwable t) {

            }
        });
    }


    /**
     * show a dialog if no internet connection
     */
    public void showAlertDialogNoInternetConnection() {
        if (!CheckNetwork.isInternetConnection(DetailActivity.this))
            alertDialogNoInternetConnection();
    }

    /**
     * set up dialog no internet connection
     */
    public void alertDialogNoInternetConnection() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
        builder.setTitle(getString(R.string.connection_failed));
        builder.setMessage(getString(R.string.no_internet));
        builder.setPositiveButton(getString(R.string.try_again), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getTrailer();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}





