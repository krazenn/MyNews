package com.example.krazenn.mynews.Controllers.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.krazenn.mynews.Controllers.WebViewActivity;
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


public class PageFragment extends Fragment {

    // 1 - Create keys for our Bundle
    private static final String KEY_POSITION = "position";

    // FOR DESIGN
    @BindView(R.id.fragment_main_recycler_view)
    RecyclerView recyclerView; // 1 - Declare RecyclerView
    @BindView(R.id.fragment_main_swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;
    List<ResultMostPopular> mNews;
    Gson gson = new Gson();
    int position;
    //FOR DATA
    private Disposable disposable;
    private List<ResultMostPopular> resultMostPopulars;
    private NyTimesArticleAdapter adapter;


    public PageFragment() {
    }


    // 2 - Method that will create a new instance of PageFragment, and add data to its bundle.
    public static PageFragment newInstance(int position) {


        // 2.1 Create new fragment
        PageFragment frag = new PageFragment();

        // 2.2 Create bundle and add it some data
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);

        return (frag);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // 3 - Get layout of PageFragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // 4 - Get widgets from layout and serialise it
        ButterKnife.bind(this, view);
        this.configureRecyclerView(); // - 4 Call during UI creation

        position = getArguments().getInt(KEY_POSITION, 0);
        this.configureSwipeRefreshLayout();

        this.executeHttpRequestWithRetrofit();
        configureOnClickRecyclerView();

        Log.e(getClass().getSimpleName(), "onCreateView called for fragment number " + position);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    // 3 - Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView() {
        // 3.1 - Reset list=
        mNews = new ArrayList<>();
        this.resultMostPopulars = new ArrayList<>();
        // 3.2 - Create adapter passing the list of article
        this.adapter = new NyTimesArticleAdapter(this.resultMostPopulars, Glide.with(this));
        // 3.3 - Attach the adapter to the recyclerview to populate items
        this.recyclerView.setAdapter(this.adapter);
        // 3.4 - Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------
    // 2 - Configure the SwipeRefreshLayout
    private void configureSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                Log.e("SwipreRefresh", gson.toJson(position));
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
                        Intent intent = new Intent(getContext(), WebViewActivity.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                        Log.e("TAG", "Position : " + url);
                    }
                });
    }

    private void executeHttpRequestWithRetrofit() {
        Log.d("list2", gson.toJson(position));
        switch (position) {
            case 0:
                executeHttpRequestWithRetrofitTopStories("home");
                break;

            case 1:
                executeHttpRequestWithRetrofitMostPopular();
                Log.e(getClass().getSimpleName(), "" + position);
                break;
            case 2:
                executeHttpRequestWithRetrofitTopStories("business");
                break;


        }
    }

    private void executeHttpRequestWithRetrofitTopStories(String section) {

        this.disposable = NyStreams.streamFetchArticleTopStories(section).subscribeWith(new DisposableObserver<ArticleList>() {


            @Override
            public void onNext(ArticleList articleLS) {
                resultMostPopulars.clear();
                resultMostPopulars = articleLS.getResults();


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

    private void executeHttpRequestWithRetrofitMostPopular() {
        this.disposable = NyStreams.streamFetchArticleMostPopular().subscribeWith(new DisposableObserver<ArticleList>() {

            @Override
            public void onNext(ArticleList articleLS) {
                resultMostPopulars.clear();
                resultMostPopulars = articleLS.getResults();

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
