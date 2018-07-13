package com.example.hung.newyorktimes.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Multimedia {

    @SerializedName("url")
    String url;

    @SerializedName("width")
    int width;

    @SerializedName("height")
    int height;

    @SerializedName("type")
    String type;

    @SerializedName("subtype")
    String subtype;

    public Multimedia() {

    }

    public String getUrl() {
        return "http://www.nytimes.com/" + url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }
}
