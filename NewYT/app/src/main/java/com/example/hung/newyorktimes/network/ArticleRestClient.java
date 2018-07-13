package com.example.hung.newyorktimes.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class ArticleRestClient {
    private static String BASE_URL = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
    private static final String API_KEY = "05c01f6333f140a4ae091b7a98fcd3a8";

    private static AsyncHttpClient client = new AsyncHttpClient();


    public static void getArticles(ArticleRequestParams requestParams, final ArticlesCallback callback) {

        RequestParams params = new RequestParams();
        params.put("api-key", API_KEY);
        params.put("page", requestParams.getPage());
        params.put("q", requestParams.getQuery());


        if (requestParams.getBeginDate() != null) {
            params.put("begin_date", requestParams.getBeginDate());
        }
        if (requestParams.getSortOrder() != null) {
            params.put("sort", requestParams.getSortOrder());
        }
        if (requestParams.getNewsDesk() != null) {
            params.put("fq", requestParams.getNewsDesk());
        }


        client.get(BASE_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                callback.onError(new Error(throwable.getMessage()));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Gson gson = new GsonBuilder().create();
                // Define Response class to correspond to the JSON response returned
                gson.fromJson(responseString, ArticleResponse.class);

                try {
                    JsonParser parser = new JsonParser();
                    JsonObject jsonObject = parser.parse(responseString).getAsJsonObject();
                    gson = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                            .create();
                    ArticleResponse articleResponse = gson.fromJson(jsonObject.get("response"), ArticleResponse.class);
                    callback.onSuccess(articleResponse);
                } catch (JsonSyntaxException ex) {
                    ex.printStackTrace();
                    callback.onError(new Error(ex.getMessage()));
                }
            }

        });

           /* @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //Log.d ("DEBUG", response.toString());

                JSONArray articleJsonResults = null;
                ArrayList<Article> articles = new ArrayList<>();

                try {

                    articleJsonResults = response.getJSONObject("response"). getJSONArray("docs");
                    Log.d ("DEBUG", articleJsonResults.toString());
                    for (int i = 0; i < articleJsonResults.length(); i++) {

                        articles.add(new Article(articleJsonResults.getJSONObject(i)));
                    }
                    ArticleResponse articleResponse = new ArticleResponse();
                    articleResponse.setArticleJsonResults(articleJsonResults);
                    articleResponse.setArticles(articles);
                    callback.onSuccess(articleResponse);

                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onError(new Error(e.getMessage()));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                callback.onError(new Error(throwable.getMessage()));
            }


        });*/


    }
}
