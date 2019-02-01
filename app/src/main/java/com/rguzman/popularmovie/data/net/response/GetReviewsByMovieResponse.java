package com.rguzman.popularmovie.data.net.response;

import com.google.gson.annotations.SerializedName;
import com.rguzman.popularmovie.domain.model.Review;

import java.util.List;

public class GetReviewsByMovieResponse {
    private int id;
    private int page;
    private List<Review> results;
    @SerializedName("status_message")
    private String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Review> getResults() {
        return results;
    }

    public void setResults(List<Review> results) {
        this.results = results;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
