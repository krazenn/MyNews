package com.example.krazenn.mynews.Models;

import com.example.krazenn.mynews.Models.Search.Response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticleList {


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("num_results")
    @Expose
    private Integer numResults;
    @SerializedName("results")
    @Expose
    private List<ResultMostPopular> results = null;

    @SerializedName("response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
    @SerializedName("docs")
    @Expose
    private List<ResultMostPopular> docs = null;



    public List<ResultMostPopular> getDocs() {
        return docs;
    }

    public void setDocs(List<ResultMostPopular> docs) {
        this.docs = docs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Integer getNumResults() {
        return numResults;
    }

    public void setNumResults(Integer numResults) {
        this.numResults = numResults;
    }

    public List<ResultMostPopular> getResults() {
        return results;
    }

    public void setResults(List<ResultMostPopular> results) {
        this.results = results;
    }

}
