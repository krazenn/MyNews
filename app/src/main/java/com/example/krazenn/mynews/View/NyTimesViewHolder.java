package com.example.krazenn.mynews.View;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.krazenn.mynews.Models.ResultMostPopular;
import com.example.krazenn.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NyTimesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txt_article_title)
    TextView textViewTitle;
    @BindView(R.id.txt_section)
    TextView textViewSection;
    @BindView(R.id.img_article)
    ImageView imageViewArticle;
    @BindView(R.id.txt_article_date)
    TextView textViewDate;


    public NyTimesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithArticle(ResultMostPopular resultMostPopular, RequestManager glide) {
        String subsection = "";
        String date;
        String url = "";

        //split date for delete clock
        date = resultMostPopular.getPublishedDate().split("T")[0];

        if (resultMostPopular.getSubsection() != null && !resultMostPopular.getSubsection().isEmpty()) {
            subsection = " > " + resultMostPopular.getSubsection();
        }

        this.textViewTitle.setText(resultMostPopular.getSection() + subsection);
        textViewDate.setText(date);
        textViewSection.setText(resultMostPopular.getTitle());
        if (resultMostPopular.getMedia() != null && !resultMostPopular.getMedia().isEmpty()) {
            url = resultMostPopular.getMedia().get(0).getMediaMetadata().get(0).getUrl();

        }

        if (resultMostPopular.getMultimedia() != null && !resultMostPopular.getMultimedia().isEmpty()) {

            url = resultMostPopular.getMultimedia().get(0).getUrl();

        }

        Glide.with(itemView.getContext()).load(url).apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background)).into(imageViewArticle);

    }
}
