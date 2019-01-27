package com.rguzman.popularmovie.data.repository.datasource.network;

import com.rguzman.popularmovie.data.repository.datasource.network.response.MovieListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    String SERVICE_ENDPOINT = "http://api.themoviedb.org/3/";

    @GET("movie/{path}")
    Call<MovieListResponse> getMovies(@Path("path") String path, @Query("api_key") String apiKey);


}
