package com.example.krazenn.mynews.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.krazenn.mynews.Models.ArticleList;
import com.example.krazenn.mynews.Models.Search.Doc;
import com.example.krazenn.mynews.R;
import com.example.krazenn.mynews.Utils.ItemClickSupport;
import com.example.krazenn.mynews.Utils.NyStreams;
import com.example.krazenn.mynews.View.NyTimesArticleSearchAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    List<Doc> resultMostPopulars;
    Gson gson = new Gson();
    String inputSearch = "";
    String dateStart;
    String dateEnd;
    NyTimesArticleSearchAdapter adapter;
    Intent intent = getIntent();
    private Disposable disposable;
    Map<String, String> params = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        ButterKnife.bind(this);


        this.configureRecyclerView(); // - 4 Call during UI creation

        this.configureSwipeRefreshLayout();

        this.executeHttpRequestWithRetrofitSearch();
        configureOnClickRecyclerView();
    }

    private void configureRecyclerView() {
        // 3.1 - Reset list=

        this.resultMostPopulars = new ArrayList<>();
        // 3.2 - Create adapter passing the list of article
        this.adapter = new NyTimesArticleSearchAdapter(resultMostPopulars, Glide.with(this));
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
                executeHttpRequestWithRetrofitSearch();
            }
        });
    }

    // 1 - Configure item click on RecyclerView
    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(recyclerView, R.layout.article_layout)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                        String url = resultMostPopulars.get(position).getWebUrl();
                        Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                    }
                });
    }


    private void executeHttpRequestWithRetrofitSearch() {
        String section;
        intent = getIntent();
        inputSearch = intent.getStringExtra("input_search");
        dateStart = intent.getStringExtra("date_start");
        dateEnd = intent.getStringExtra("date_end");
        section = intent.getStringExtra("section");
        params.put("q", inputSearch);
        if (dateStart != null) {
            params.put("begin_date", dateStart);

        }
        if (dateEnd != null) {
            params.put("end_date", dateEnd);

        }
        if (!section.isEmpty()) {
            params.put("fq", "news_desk:(" + section + ")");
            Log.e("sectionResult,", section);
        }
        this.disposable = NyStreams.streamFetchArticleSearch(params, "hKPJScQIKlhcQ3V0GmlDulzquyM28AGL").subscribeWith(new DisposableObserver<ArticleList>() {

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }
}
