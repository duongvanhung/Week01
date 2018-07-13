package com.example.hung.newyorktimes.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Byline {

    @SerializedName("original")
     String original;

    public Byline() {}

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

}
