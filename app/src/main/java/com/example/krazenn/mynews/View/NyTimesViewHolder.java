package com.example.krazenn.mynews.View;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.bumptech.glide.RequestManager;
import com.example.krazenn.mynews.Models.ResultMostPopular;
import com.example.krazenn.mynews.R;
import com.google.gson.Gson;

public class NyTimesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txt_article_title)
    TextView textViewTitle;
    @BindView(R.id.txt_section)
    TextView textViewSection;
    @BindView(R.id.img_article)
    ImageView imageViewArticle;
    String url;

    public NyTimesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithArticle(ResultMostPopular resultMostPopular, RequestManager glide) {

        this.textViewTitle.setText(resultMostPopular.getTitle());
        textViewSection.setText(resultMostPopular.getSection());
        if (resultMostPopular.getMedia() != null) {
            url = resultMostPopular.getMedia().get(0).getMediaMetadata().get(0).getUrl();

        }

        if (resultMostPopular.getMultimedia() != null && !resultMostPopular.getMultimedia().isEmpty()) {

            url = resultMostPopular.getMultimedia().get(0).getUrl();

        }


        glide.load(url).into(imageViewArticle);


    }
}
