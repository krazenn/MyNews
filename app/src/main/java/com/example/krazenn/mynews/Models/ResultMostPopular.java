package com.example.krazenn.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultMostPopular {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("count_type")
    @Expose
    private String countType;
    @SerializedName("column")
    @Expose
    private String column;
    @SerializedName("section")
    @Expose
    private String section;

    @SerializedName("byline")
    @Expose
    private String byline;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("subsection")
    @Expose
    private String subsection;
    @SerializedName("abstract")
    @Expose
    private String _abstract;
    @SerializedName("published_date")
    @Expose
    private String publishedDate;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("short_url")
    @Expose
    private String short_url;

    @SerializedName("multimedia")
    @Expose
    private List<Medium> multimedia = null;
    @SerializedName("docs")
    @Expose
    private List<ResultMostPopular> docs = null;
    @SerializedName("media")
    @Expose
    private List<Medium> media = null;
    private String ImageUrl = null;

    @SerializedName("snippet")
    @Expose
    private String snippet;
    @SerializedName("updated_date")
    @Expose
    private String updated_date;

    public String getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(String updated_date) {
        this.updated_date = updated_date;
    }

    public String getSubsection() {
        return subsection;
    }

    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getShort_url() {
        return short_url;
    }

    public void setShort_url(String short_url) {
        this.short_url = short_url;
    }

    public List<Medium> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<Medium> multimedia) {
        this.multimedia = multimedia;
    }

    public List<ResultMostPopular> getDocs() {
        return docs;
    }

    public void setDocs(List<ResultMostPopular> docs) {
        this.docs = docs;
    }

    public String get_abstract() {
        return _abstract;
    }

    public void set_abstract(String _abstract) {
        this._abstract = _abstract;
    }

    public List<Medium> getMedia() {
        return media;
    }

    public void setMedia(List<Medium> media) {
        this.media = media;
    }

    public String getImageUrl() {
        if (getMedia() != null) {
            ImageUrl = media.get(0).getMediaMetadata().get(0).getUrl();

        }

        if (multimedia != null && !multimedia.isEmpty()) {

            url = multimedia.get(0).getUrl();

        }
        return ImageUrl;
    }

    public void setImageUrl(String getImageUrl) {
        this.ImageUrl = getImageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCountType() {
        return countType;
    }

    public void setCountType(String countType) {
        this.countType = countType;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getByline() {
        return byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstract() {
        return _abstract;
    }

    public void setAbstract(String _abstract) {
        this._abstract = _abstract;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


}