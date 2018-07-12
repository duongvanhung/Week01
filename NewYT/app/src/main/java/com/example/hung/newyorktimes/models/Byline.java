package com.example.hung.newyorktimes.models;

import com.google.gson.annotations.SerializedName;

public class Byline {

    @SerializedName("original")
    private String original;

    public Byline() {}

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

}
