package com.example.krazenn.mynews.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.example.krazenn.mynews.Models.ResultMostPopular;
import com.example.krazenn.mynews.R;
import com.google.gson.Gson;

import java.util.List;

public class NyTimesArticleAdapter extends RecyclerView.Adapter<NyTimesViewHolder> {
    // CONSTRUCTOR
    Gson gson = new Gson();
    private RequestManager glide;
    // FOR DATA
    private List<ResultMostPopular> mostPopularList;

    public NyTimesArticleAdapter(List<ResultMostPopular> resultMostPopularList, RequestManager glide) {
        this.mostPopularList = resultMostPopularList;
        this.glide = glide;
    }

    @Override
    public NyTimesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.article_layout, parent, false);

        return new NyTimesViewHolder(view);
    }

    // UPDATE VIEW HOLDER WITH A ARTICLE
    @Override
    public void onBindViewHolder(NyTimesViewHolder viewHolder, int position) {

        viewHolder.updateWithArticle(this.mostPopularList.get(position), this.glide);


    }

    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST

    @Override
    public int getItemCount() {
        return mostPopularList.size();
    }

    public void setData(List<ResultMostPopular> resultMostPopularList) {

        this.mostPopularList = resultMostPopularList;
        notifyDataSetChanged();

    }
}
