package com.example.usphuong.thenewyorktimes.utils;
import com.example.usphuong.thenewyorktimes.api.ApiService;
import com.example.usphuong.thenewyorktimes.models.ResponseWrapper;

import java.io.IOException;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;


public class RetrofitUtil {

    private ApiService service;


    private static Retrofit builder() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiService.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client())
                .build();
    }

    private static OkHttpClient client() {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(chain -> {
                    Request request = chain.request();
                    HttpUrl url = request.url()
                            .newBuilder()
                            .addQueryParameter("api_key", ApiService.API_KEY)
                            .build();
                    request = request.newBuilder()
                            .url(url)
                            .build();
                    return chain.proceed(request);
                })
                .build();
    }

    public static  ApiService createService() {
        return builder().create(ApiService.class);
    }


}
