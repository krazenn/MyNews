package com.example.krazenn.mynews;

import com.example.krazenn.mynews.Models.ArticleList;
import com.example.krazenn.mynews.Models.ResultMostPopular;
import com.example.krazenn.mynews.Utils.NyStreams;
import com.example.krazenn.mynews.View.NyTimesArticleAdapter;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class RequestRetrofit {
    public Disposable disposable;
    public List<ResultMostPopular> resultMostPopulars;
    public NyTimesArticleAdapter adapter;
    public RequestListener listener;

    public void setListener(RequestListener listener) {
        this.listener = listener;
    }

    public void executeHttpRequestWithRetrofitMostPopular() {
        this.disposable = NyStreams.streamFetchArticleMostPopular().subscribeWith(new DisposableObserver<ArticleList>() {

            @Override
            public void onNext(ArticleList articleLS) {
                listener.onReceive(articleLS);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
            }
        });

    }

    public void executeHttpRequestWithRetrofitTopStories(String section) {

        this.disposable = NyStreams.streamFetchArticleTopStories(section).subscribeWith(new DisposableObserver<ArticleList>() {


            @Override
            public void onNext(ArticleList articleLS) {
                listener.onReceive(articleLS);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
            }
        });
    }


    public interface RequestListener {
        void onReceive(ArticleList articleLS);
    }

}
