package com.example.krazenn.mynews.Utils;

import com.example.krazenn.mynews.Models.ArticleList;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NyStreams {

    public static Observable<ArticleList> streamFetchArticleMostPopular() {
        NyTimesService nyTimesService = NyTimesService.retrofit.create(NyTimesService.class);
        return nyTimesService.getArticleMostPopular()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<ArticleList> streamFetchArticleTopStories(String section) {
        NyTimesService nyTimesService = NyTimesService.retrofit.create(NyTimesService.class);
        return nyTimesService.getArticleTopStories(section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<ArticleList> streamFetchArticleSearch(String search, String key) {
        NyTimesService nyTimesService = NyTimesService.retrofit.create(NyTimesService.class);
        return nyTimesService.getArticleSearch(search, key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}