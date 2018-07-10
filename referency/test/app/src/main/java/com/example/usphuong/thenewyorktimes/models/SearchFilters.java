package com.example.usphuong.thenewyorktimes.models;

import android.util.Log;

import org.parceler.Parcel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


@Parcel
public class SearchFilters {


    public static final String SORT_OLDEST = "oldest";
    public static final String SORT_NEWEST = "newest";

    public static final String NEWS_DESK_ARTS = "\"Arts\"";
    public static final String NEWS_DESK_FASHION = "\"Fashion\"";
    public static final String NEWS_DESK_STYLE = "\"Style\"";
    public static final String NEWS_DESK_SPORT = "\"Sport\"";

    private static final String TAG = "NY: SearchFilters";

    private String query;
    private String beginDateString;

    private boolean sortOldest;

    private boolean arts;
    private boolean fashion;
    private boolean style;
    private boolean sport;

    private boolean ignoreBeginDate;

    private static SimpleDateFormat queryDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.US);

    public SearchFilters() { reset(); }

    public void setQuery(String query) {
        this.query = query;
    }

    public void reset() {
        query = null;
        sortOldest = false;
        arts = fashion = style = sport = true;

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_MONTH, -1);
        beginDateString = queryDateFormat.format(calendar.getTime());

        ignoreBeginDate = true;
    }

    public void resetQuery() {
        query = null;
    }

    public void update(Date beginDate, boolean oldest, boolean isArts, boolean isFashion, boolean isStyle, boolean isSport) {

        beginDateString = queryDateFormat.format(beginDate);
        ignoreBeginDate = false;
        sortOldest = oldest;
        arts = isArts;
        fashion = isFashion;
        style = isStyle;
        sport = isSport;

    }

    public void update(boolean oldest, boolean isArts, boolean isFashion, boolean isStyle, boolean isSport) {
        ignoreBeginDate = true;
        sortOldest = oldest;
        arts = isArts;
        fashion = isFashion;
        style = isStyle;
        sport = isSport;

    }

    public String getQuery() {
        return query;
    }

    public String getBeginDateString() {
        return beginDateString;
    }

    public String getSortOrder() {
         return sortOldest ? SORT_OLDEST : SORT_NEWEST;
    }

    public boolean isSortOldest() {
        return sortOldest;
    }

    public boolean isArts() {
        return arts;
    }

    public boolean isStyle() {
        return style;
    }

    public boolean isFashion() {
        return fashion;
    }

    public boolean isSport() {
        return sport;
    }

    public boolean isIgnoreBeginDate() {
        return ignoreBeginDate;
    }

    public static SimpleDateFormat getQueryDateFormat() {
        return queryDateFormat;
    }

    public String getNewsDesk() {

        List<String> newsDesks = new ArrayList<>();

        if (arts) newsDesks.add(NEWS_DESK_ARTS);
        if (fashion) newsDesks.add(NEWS_DESK_FASHION);
        if (style) newsDesks.add(NEWS_DESK_STYLE);
        if (sport) newsDesks.add(NEWS_DESK_SPORT);

        StringBuilder builder = new StringBuilder();
        String joinedString = join(newsDesks, " ");
        if (!joinedString.isEmpty()) {

            builder.append("news_desk:(").append(joinedString).append(")");
        }

        String result = builder.toString();
        return result.isEmpty() ? null : result;

    }

    private static String join(List<String> list, String delim) {

        StringBuilder sb = new StringBuilder();

        String loopDelim = "";

        for(String s : list) {

            sb.append(loopDelim);
            sb.append(s);

            loopDelim = delim;
        }

        Log.d(TAG, sb.toString());
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder().append("SearchFilter: ")
                .append("Q: ").append(query)
                .append(" sort = ").append(getSortOrder()).append(" ")
                .append(getNewsDesk())
                .append(" begin date: ").append(beginDateString);

        return builder.toString();
    }
}
