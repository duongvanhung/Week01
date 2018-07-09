package com.example.luba.nytimessearch.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by luba on 9/24/17.
 */


@Parcel
public class Byline {

    public Byline() {}

    @SerializedName("original")
    String original;

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }
}
