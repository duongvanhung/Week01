package com.androidcoban.flicks.Data;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.androidcoban.flicks.Adapter.AdapterMovies;
import com.androidcoban.flicks.Adapter.ClickItem;
import com.androidcoban.flicks.Api.ApiSources;
import com.androidcoban.flicks.Api.CheckNetwork;
import com.androidcoban.flicks.Models.MoviesApi;
import com.androidcoban.flicks.Models.PlayNowMovies;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    List<MoviesApi> list_movies;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;


    AdapterMovies adapterMovies;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (list_movies == null) {
        init();
        getAllMovies();
       } else {
          adapterMovies.setData(list_movies);
        }

    }

    /**
     * Get movie data from API
     */
    void getAllMovies() {
        showAlertDialogNoInternetConnection();
        showAlertDialogNoInternetConnection();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiSources.Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiSources retrofitClient = retrofit.create(ApiSources.class);
        Call<PlayNowMovies> call = retrofitClient.getPlayNowMovies(ApiSources.Api_key);
        call.enqueue(new Callback<PlayNowMovies>() {
            @Override
            public void onResponse(@NonNull Call<PlayNowMovies> call, @NonNull Response<PlayNowMovies> response) {
                if (response.body() != null) {
                    adapterMovies.setData(response.body().getMovies());
                    recyclerView.setAdapter(adapterMovies);
                }
            }


            @Override
            public void onFailure(@NonNull Call<PlayNowMovies> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });


    }

    /**
     * Initialize for RecyclerView
     */
    void init() {
        list_movies = new ArrayList<>();

        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapterMovies = new AdapterMovies(MainActivity.this);
        adapterMovies.setData(list_movies);

        /*
          Handle what happened when click on item of recycler view
         */
        adapterMovies.setItemClick(new ClickItem() {
            @Override
            public void onClickItem(MoviesApi movie) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("movie", movie);
                startActivity(intent);
            }

        });


        /*
          set up swipe refresh layout
         */
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override

            public void onRefresh() {

                adapterMovies.clearData();

                new Handler().postDelayed(new Runnable(){
                    @Override public void run() {
                    swipeRefreshLayout.setRefreshing(false);
                        }
                    },1000);
                getAllMovies();


            }

    });

        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );

    }



    /**
     * show a dialog if no internet connection
     */
    public void showAlertDialogNoInternetConnection() {
        if (!CheckNetwork.isInternetConnection(MainActivity.this))
            alertDialogNoInternetConnection();
    }

    /**
     * a dialog: no internet connection
     */
    public void alertDialogNoInternetConnection() {
        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getString(R.string.connection_failed));
        builder.setMessage(getString(R.string.no_internet));
        builder.setPositiveButton(getString(R.string.try_again), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                init();
                getAllMovies();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}





