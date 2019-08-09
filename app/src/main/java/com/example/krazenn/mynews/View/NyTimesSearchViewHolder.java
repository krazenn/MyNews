package com.example.krazenn.mynews.View;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.krazenn.mynews.Models.Search.Doc;
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
    String url = "";
    String staticUrl = "";

    public NyTimesSearchViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithArticle(Doc resultSearch, RequestManager glide) {


        textViewTitle.setText(resultSearch.getSource());
        textViewSection.setText(resultSearch.getLeadParagraph());
        if (!resultSearch.getMultimedia().isEmpty()) {
            url = resultSearch.getMultimedia().get(0).getUrl();
        }

        if (!url.isEmpty()) {
            staticUrl = "https://static01.nyt.com/";
        }
        Glide.with(itemView.getContext()).load(staticUrl + url).apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background)).into(imageViewArticle);
    }
}
