package com.example.hung.newyorktimes.models;


import com.google.gson.annotations.SerializedName;

public class Headline {

    @SerializedName("main")

    private String main;

    @SerializedName("name")

    private String name;

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


    public String getTitle(){
        if(getMain() != null && !"".equals(getMain())){
            return getMain();
        }else{
            return getName();
        }

    }

}
