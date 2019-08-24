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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public void updateWithArticle(ResultMostPopular result, RequestManager glide) {
        String subsection = "";
        String date;
        String url = "";

        //split date for delete clock
        date = formatDate(result.getPublishedDate().split("T")[0]);


        if (result.getSubsection() != null && !result.getSubsection().isEmpty()) {
            subsection = " > " + result.getSubsection();
        }

        this.textViewTitle.setText(result.getSection() + subsection);
        textViewDate.setText(date);
        textViewSection.setText(result.getTitle());
        if (result.getMedia() != null && !result.getMedia().isEmpty()) {
            url = result.getMedia().get(0).getMediaMetadata().get(0).getUrl();

        }

        if (result.getMultimedia() != null && !result.getMultimedia().isEmpty()) {

            url = result.getMultimedia().get(0).getUrl();

        }

        Glide.with(itemView.getContext()).load(url).apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background)).into(imageViewArticle);

    }

    private String formatDate(String stringDate) {
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = spf.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf = new SimpleDateFormat("dd/MM/YYYY");
        stringDate = spf.format(date);
        return stringDate;

    }
}
