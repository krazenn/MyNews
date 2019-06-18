package com.example.krazenn.mynews.Controllers;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.example.krazenn.mynews.Controllers.Fragment.PageFragment;
import com.example.krazenn.mynews.Models.ArticleList;
import com.example.krazenn.mynews.Models.ResultMostPopular;
import com.example.krazenn.mynews.R;
import com.example.krazenn.mynews.Utils.ItemClickSupport;
import com.example.krazenn.mynews.Utils.NyStreams;
import com.example.krazenn.mynews.View.NyTimesArticleAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class ResultSearchActivity extends AppCompatActivity {
    // FOR DESIGN
    @BindView(R.id.fragment_main_recycler_view)
    RecyclerView recyclerView; // 1 - Declare RecyclerView
    @BindView(R.id.fragment_main_swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;
    List<ResultMostPopular> resultMostPopulars;
    Gson gson = new Gson();
    String inputSearch = "";
    PageFragment pageFragment = new PageFragment();
    NyTimesArticleAdapter adapter;
    Intent intent = getIntent();
    private Disposable disposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        ButterKnife.bind(this);


        this.configureRecyclerView(); // - 4 Call during UI creation

        this.configureSwipeRefreshLayout();

        this.executeHttpRequestWithRetrofit();
        configureOnClickRecyclerView();
    }

    private void configureRecyclerView() {
        // 3.1 - Reset list=

        this.resultMostPopulars = new ArrayList<>();
        // 3.2 - Create adapter passing the list of article
        this.adapter = new NyTimesArticleAdapter(this.resultMostPopulars, Glide.with(this));
        // 3.3 - Attach the adapter to the recyclerview to populate items
        this.recyclerView.setAdapter(this.adapter);
        // 3.4 - Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------
    // 2 - Configure the SwipeRefreshLayout
    private void configureSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                executeHttpRequestWithRetrofit();
            }
        });
    }

    // 1 - Configure item click on RecyclerView
    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(recyclerView, R.layout.article_layout)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                        String url = resultMostPopulars.get(position).getUrl();
                        Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                        Log.e("TAG", "Position : " + url);
                    }
                });
    }

    private void executeHttpRequestWithRetrofit() {
        intent = getIntent();
        inputSearch = intent.getStringExtra("input_search");
        executeHttpRequestWithRetrofitSearch(inputSearch);

    }


    private void executeHttpRequestWithRetrofitSearch(String search) {
        this.disposable = NyStreams.streamFetchArticleSearch(search, "hKPJScQIKlhcQ3V0GmlDulzquyM28AGL").subscribeWith(new DisposableObserver<ArticleList>() {


            @Override
            public void onNext(ArticleList articleLS) {
                resultMostPopulars.clear();
                resultMostPopulars = articleLS.getResponse().getDocs();

                adapter.setData(resultMostPopulars);
                swipeRefreshLayout.setRefreshing(false);
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

    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }
}
