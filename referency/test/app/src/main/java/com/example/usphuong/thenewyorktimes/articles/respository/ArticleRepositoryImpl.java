package com.example.usphuong.thenewyorktimes.articles.respository;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.usphuong.thenewyorktimes.adapter.RecyclerViewAdapter;
import com.example.usphuong.thenewyorktimes.api.ApiService;
import com.example.usphuong.thenewyorktimes.models.Doc;
import com.example.usphuong.thenewyorktimes.models.ResponseWrapper;
import com.example.usphuong.thenewyorktimes.models.SearchFilters;
import com.example.usphuong.thenewyorktimes.utils.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class ArticleRepositoryImpl implements ArticleRepository{

    private static final String TAG = "NY: ";

    private  ApiService service;

    public ArticleRepositoryImpl() { service = RetrofitUtil.createService();
    }

    @Override
    public void getData(DataListener listener, Integer page, SearchFilters filters) {

        service.getArticles(page, filters.getSortOrder(),
                filters.getQuery(), filters.isIgnoreBeginDate() ? null : filters.getBeginDateString(), filters.getNewsDesk())
                .enqueue(new Callback<ResponseWrapper>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseWrapper> call, @NonNull Response<ResponseWrapper> response) {
                        if (response.body() != null && Objects.requireNonNull(response.body()).getResponse().getDocs() != null) {
                            List<Doc> docs = Objects.requireNonNull(response.body()).getResponse().getDocs();
                            listener.onResponse(docs);

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseWrapper> call, Throwable t) {
                        listener.onError(t.getMessage());
                    }
                });

    }

}
