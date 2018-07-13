package com.example.hung.newyorktimes.models;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Date;



@Parcel
public class Article{


    @SerializedName("web_url")
    String mWebURL;

    @SerializedName("headline")
    Headline mHeadline;

    @SerializedName("url")
    String mThumbNail;

    @SerializedName("pub_date")
    Date mPubDate;

    @SerializedName("byline")
    Byline mByLine;

    @SerializedName("news_desk")
    String mNewsDesk;

    @SerializedName("multimedia")
    ArrayList<Multimedia> mMultimedia;


    public Article(){

    }


    public String getWebURL() {
        return mWebURL;
    }
    public void setWebUrl(String webUrl) {
        this.mWebURL = mWebURL;
    }

    public Headline getHeadline() {
        return mHeadline;
    }

    public void setHeadline(Headline mHeadline) {
        this.mHeadline = mHeadline;
    }


    public ArrayList<Multimedia> getMultimedia() {
        return mMultimedia;
    }

    public void setMultimedia(ArrayList<Multimedia> mMultimedia) {
        this.mMultimedia = mMultimedia;
    }


    public Date getPubDate() {
        return mPubDate;
    }

    public void setPubDate(Date mPubDate) {
        this.mPubDate = mPubDate;
    }


    public Byline getByLine() {
        return mByLine;
    }

    public void setByLine(Byline mByLine) {
        this.mByLine = mByLine;
    }



    public boolean hasImages() {
        return this.mMultimedia != null && this.mMultimedia.size() > 0;
    }

    public Multimedia getFirstImageFromMultimedia() {
        if (this.mMultimedia != null && this.mMultimedia.size() > 0) {
            return this.mMultimedia.get(0);
        }
        return null;
    }


}
