package com.rguzmanc.popularmovie.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.rguzmanc.popularmovie.model.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class MovieJsonUtils {

    public static List<Movie> getListMoviesFromJson(Context context, String moviesJson)
            throws Exception {

        final String STATUS_CODE = "status_code";
        final String STATUS_MESSAGE = "status_message";
        final String KEY_RESULT = "results";

        final String KEY_MOVIE_VOTE_COUNT = "vote_count";
        final String KEY_MOVIE_ID = "id";
        final String KEY_MOVIE_VIDEO = "video";
        final String KEY_MOVIE_VOTE_AVERAGE = "vote_average";
        final String KEY_MOVIE_TITLE = "title";
        final String KEY_MOVIE_POPULARITY = "popularity";
        final String KEY_MOVIE_POSTER_PATH = "poster_path";
        final String KEY_MOVIE_ORIGINAL_LANGUAGE = "original_language";
        final String KEY_MOVIE_ORIGINAL_TITLE = "original_title";
        final String KEY_MOVIE_GENRE_IDS = "genre_ids";
        final String KEY_MOVIE_BACKDROP_PATH = "backdrop_path";
        final String KEY_MOVIE_ADULT = "adult";
        final String KEY_MOVIE_OVERVIEW = "overview";
        final String KEY_MOVIE_RELEASE_DATE = "release_date";

        final String BASE_REQUEST_IMAGE_URL = "http://image.tmdb.org/t/p/";
        final String REQUEST_IMAGE_SIZE_PATH = "w185";

        final int API_KEY_ERROR_CODE = 7;

        List<Movie> movies = new ArrayList<>();
        JSONObject moviesListJson = new JSONObject(moviesJson);

        if (moviesListJson.has(STATUS_CODE) && moviesListJson.has(STATUS_MESSAGE)) {
            int errorCode = moviesListJson.getInt(STATUS_CODE);

            switch (errorCode) {
                case API_KEY_ERROR_CODE:
                    throw new Exception(moviesListJson.getString(STATUS_MESSAGE));
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }

        if (moviesListJson.has(KEY_RESULT)) {
            JSONArray moviesListJsonArray = moviesListJson.getJSONArray(KEY_RESULT);
            for (int i = 0; i < moviesListJsonArray.length(); i++) {
                JSONObject movieJsonObject = moviesListJsonArray.getJSONObject(i);
                Movie movie = new Movie();

                if (movieJsonObject.has(KEY_MOVIE_VOTE_COUNT)) {
                    int voteCount = movieJsonObject.getInt(KEY_MOVIE_VOTE_COUNT);
                    movie.setVoteCount(voteCount);
                }

                if (movieJsonObject.has(KEY_MOVIE_ID)) {
                    int id = movieJsonObject.getInt(KEY_MOVIE_ID);
                    movie.setId(id);
                }

                if (movieJsonObject.has(KEY_MOVIE_VIDEO)) {
                    boolean video = movieJsonObject.getBoolean(KEY_MOVIE_VIDEO);
                    movie.setVideo(video);
                }

                if (movieJsonObject.has(KEY_MOVIE_VOTE_AVERAGE)) {
                    double voteAverage = movieJsonObject.getDouble(KEY_MOVIE_VOTE_AVERAGE);
                    movie.setVoteAverage(voteAverage);
                }

                if (movieJsonObject.has(KEY_MOVIE_TITLE)) {
                    String title = movieJsonObject.getString(KEY_MOVIE_TITLE);
                    movie.setTitle(title);
                }

                if (movieJsonObject.has(KEY_MOVIE_POPULARITY)) {
                    double popularity = movieJsonObject.getDouble(KEY_MOVIE_POPULARITY);
                    movie.setPopularity(popularity);
                }

                if (movieJsonObject.has(KEY_MOVIE_POSTER_PATH)) {

                    String path = movieJsonObject.getString(KEY_MOVIE_POSTER_PATH);

                    Uri builtUri = Uri.parse(BASE_REQUEST_IMAGE_URL).buildUpon()
                            .appendPath(REQUEST_IMAGE_SIZE_PATH)
                            .appendEncodedPath(path)
                            .build();

                    movie.setPosterPath(builtUri.toString());
                }

                if (movieJsonObject.has(KEY_MOVIE_ORIGINAL_LANGUAGE)) {
                    String originalLanguage = movieJsonObject.getString(KEY_MOVIE_ORIGINAL_LANGUAGE);
                    movie.setOriginalLanguage(originalLanguage);
                }

                if (movieJsonObject.has(KEY_MOVIE_ORIGINAL_TITLE)) {
                    String originalTitle = movieJsonObject.getString(KEY_MOVIE_ORIGINAL_TITLE);
                    movie.setOriginalTitle(originalTitle);
                }

                if (movieJsonObject.has(KEY_MOVIE_GENRE_IDS)) {
                    List<Integer> genreIds = new ArrayList<>();
                    JSONArray genreIdsJsonArray = movieJsonObject.getJSONArray(KEY_MOVIE_GENRE_IDS);
                    for (int j = 0; j < genreIdsJsonArray.length(); j++) {
                        genreIds.add(genreIdsJsonArray.getInt(j));
                    }
                    movie.setGenreIds(genreIds);
                }

                if (movieJsonObject.has(KEY_MOVIE_BACKDROP_PATH)) {
                    String backdropPath = movieJsonObject.getString(KEY_MOVIE_BACKDROP_PATH);
                    movie.setBackdropPath(backdropPath);
                }

                if (movieJsonObject.has(KEY_MOVIE_ADULT)) {
                    boolean adult = movieJsonObject.getBoolean(KEY_MOVIE_ADULT);
                    movie.setAdult(adult);
                }

                if (movieJsonObject.has(KEY_MOVIE_OVERVIEW)) {
                    String overview = movieJsonObject.getString(KEY_MOVIE_OVERVIEW);
                    movie.setOverview(overview);
                }

                if (movieJsonObject.has(KEY_MOVIE_RELEASE_DATE)) {
                    movie.setReleaseDate(movieJsonObject.getString(KEY_MOVIE_RELEASE_DATE));
                }
                movies.add(movie);
            }
        }

        return movies;
    }
}
