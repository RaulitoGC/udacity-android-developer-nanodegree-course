package com.rguzman.popularmovie.data.net.response;

import com.google.gson.annotations.SerializedName;
import com.rguzman.popularmovie.domain.model.Video;

import java.util.List;

public class GetVideosByMovieResponse {
    private int id;
    private List<Video> results;
    @SerializedName("status_message")
    private String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
