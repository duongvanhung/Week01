package com.example.usphuong.thenewyorktimes.articles.respository;

import com.example.usphuong.thenewyorktimes.models.Doc;
import com.example.usphuong.thenewyorktimes.models.Response;

import java.util.List;

public interface DataListener {

    void onResponse(List<Doc> docs);

    void onError(String error);
}
