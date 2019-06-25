package com.example.krazenn.mynews.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.example.krazenn.mynews.Models.Search.Doc;
import com.example.krazenn.mynews.R;
import com.google.gson.Gson;

import java.util.List;

public class NyTimesArticleSearchAdapter extends RecyclerView.Adapter<NyTimesSearchViewHolder> {
    // CONSTRUCTOR
    Gson gson = new Gson();
    private RequestManager glide;
    // FOR DATA
    private List<Doc> resultSearchList;

    public NyTimesArticleSearchAdapter(List<Doc> resultMostPopularList, RequestManager glide) {
        this.resultSearchList = resultMostPopularList;
        this.glide = glide;
    }

    @Override
    public NyTimesSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.article_layout, parent, false);

        return new NyTimesSearchViewHolder(view);
    }

    // UPDATE VIEW HOLDER WITH A ARTICLE
    @Override
    public void onBindViewHolder(NyTimesSearchViewHolder viewHolder, int position) {

        viewHolder.updateWithArticle(this.resultSearchList.get(position), this.glide);






    }

    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST

    @Override
    public int getItemCount() {
        return resultSearchList.size();
    }

    public void setData(List<Doc> resultMostPopularList) {

        this.resultSearchList = resultMostPopularList;
        notifyDataSetChanged();

    }
}
