package com.example.krazenn.mynews.View;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.krazenn.mynews.Models.ResultMostPopular;
import com.example.krazenn.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NyTimesSearchViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txt_article_title)
    TextView textViewTitle;
    @BindView(R.id.txt_section)
    TextView textViewSection;
    @BindView(R.id.img_article)
    ImageView imageViewArticle;
    String url;

    public NyTimesSearchViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithArticle(ResultMostPopular resultMostPopular, RequestManager glide) {

        textViewSection.setText(resultMostPopular.getSource());
        if (resultMostPopular.getMedia() != null) {
            url = resultMostPopular.getMedia().get(0).getMediaMetadata().get(0).getUrl();

        }

        if (resultMostPopular.getMultimedia() != null && !resultMostPopular.getMultimedia().isEmpty()) {

            url = resultMostPopular.getMultimedia().get(0).getUrl();

        }


        glide.load(url).into(imageViewArticle);


    }
}
