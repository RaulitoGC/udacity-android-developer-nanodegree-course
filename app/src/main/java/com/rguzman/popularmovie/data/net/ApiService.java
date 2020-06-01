package com.rguzman.popularmovie.data.net;

import com.rguzman.popularmovie.data.net.response.GetReviewsByMovieResponse;
import com.rguzman.popularmovie.data.net.response.GetVideosByMovieResponse;
import com.rguzman.popularmovie.data.net.response.MovieListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    String SERVICE_ENDPOINT = "http://api.themoviedb.org/3/";

    @GET("movie/popular")
    Call<MovieListResponse> loadPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieListResponse> loadTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<GetVideosByMovieResponse> loadVideosByMovie(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    Call<GetReviewsByMovieResponse> loadReviewsByMovie(@Path("id") int id, @Query("api_key") String apiKey);

}
