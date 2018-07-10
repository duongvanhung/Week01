
package com.example.usphuong.thenewyorktimes.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {

    @SerializedName("docs")
    @Expose
    private List<Doc> docs = null;

    public List<Doc> getDocs() {
        return docs;
    }

}
