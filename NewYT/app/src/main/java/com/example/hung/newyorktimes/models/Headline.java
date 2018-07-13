package com.example.hung.newyorktimes.models;


import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Headline {

    @SerializedName("main")
    String main;

    @SerializedName("name")
    String name;

    public Headline() {}

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        if (getMain() != null && !"".equals(getMain())) {
            return getMain();
        } else {
            return getName();
        }
    }


}
