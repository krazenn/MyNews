package com.example.krazenn.mynews.Controllers.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.krazenn.mynews.Controllers.WebViewActivity;
import com.example.krazenn.mynews.Models.ArticleList;
import com.example.krazenn.mynews.Models.ResultMostPopular;
import com.example.krazenn.mynews.R;
import com.example.krazenn.mynews.RequestRetrofit;
import com.example.krazenn.mynews.Utils.ItemClickSupport;
import com.example.krazenn.mynews.View.NyTimesArticleAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;


public class PageFragment extends Fragment {

    // 1 - Create keys for our Bundle
    private static final String KEY_POSITION = "position";

    // FOR DESIGN
    @BindView(R.id.fragment_main_recycler_view)
    RecyclerView recyclerView; // 1 - Declare RecyclerView
    @BindView(R.id.fragment_main_swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.app_bar)
    Toolbar toolbar;
    List<ResultMostPopular> mNews;
    Gson gson = new Gson();
    int position;
    //FOR DATA
    private Disposable disposable;
    private List<ResultMostPopular> result;
    private NyTimesArticleAdapter adapter;

    RequestRetrofit requestRetrofit = new RequestRetrofit();

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
        toolbar.setVisibility(View.GONE);
        this.configureRecyclerView(); // - 4 Call during UI creation

        position = getArguments().getInt(KEY_POSITION, 0);
        this.configureSwipeRefreshLayout();
        retrofitListener();
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
        this.result = new ArrayList<>();
        // 3.2 - Create adapter passing the list of article
        this.adapter = new NyTimesArticleAdapter(this.result, Glide.with(this));
        // 3.3 - Attach the adapter to the recyclerview to populate items
        this.recyclerView.setAdapter(this.adapter);
        // 3.4 - Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    //  Configure the SwipeRefreshLayout
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

                        String url = result.get(position).getUrl();
                        Intent intent = new Intent(getContext(), WebViewActivity.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                        Log.e("TAG", "Position : " + url);
                    }
                });
    }

    private void executeHttpRequestWithRetrofit() {

        switch (position) {
            case 0:
                requestRetrofit.executeHttpRequestWithRetrofitTopStories("home");
                break;
            case 1:
                requestRetrofit.executeHttpRequestWithRetrofitMostPopular();
                break;
            case 2:
                requestRetrofit.executeHttpRequestWithRetrofitTopStories("business");
                break;
        }
    }

    //Setup Retrofit Listener
    private void retrofitListener() {
        requestRetrofit.setListener(new RequestRetrofit.RequestListener() {
            @Override
            public void onReceive(ArticleList articleLS) {
                result.clear();
                result = articleLS.getResults();
                adapter.setData(result);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }
}
