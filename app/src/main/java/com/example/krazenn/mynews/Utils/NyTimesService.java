package com.example.krazenn.mynews.Utils;


import com.example.krazenn.mynews.CustomHttpClient;
import com.example.krazenn.mynews.Models.ArticleList;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface NyTimesService {

    String API_KEY = "api-key=hKPJScQIKlhcQ3V0GmlDulzquyM28AGL";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(CustomHttpClient.getCustomClient())
            .build();

    @GET("svc/mostpopular/v2/viewed/7.json?api-key=hKPJScQIKlhcQ3V0GmlDulzquyM28AGL")
    Observable<ArticleList> getArticleMostPopular();

    @GET("svc/search/v2/articlesearch.json")
    Observable<ArticleList> getArticleSearch(@QueryMap Map<String, String> params, @Query("api-key") String key);

    @GET("svc/topstories/v2/{section}.json?api-key=hKPJScQIKlhcQ3V0GmlDulzquyM28AGL")
    Observable<ArticleList> getArticleTopStories(@Path("section") String section);


}
