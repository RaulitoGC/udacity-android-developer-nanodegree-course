package com.rguzman.popularmovie.data.net.response;

import com.google.gson.annotations.SerializedName;
import com.rguzman.popularmovie.domain.model.Movie;

import java.util.List;

public class MovieListResponse {

    @SerializedName("status_code")
    private String code;
    @SerializedName("status_message")
    private String message;

    private int page;
    @SerializedName("total_results")
    private int totalResult;
    @SerializedName("total_pages")
    private int totalPages;
    private List<Movie> results;

    public MovieListResponse(int page, int totalResult, int totalPages, List<Movie> results) {
        this.page = page;
        this.totalResult = totalResult;
        this.totalPages = totalPages;
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
