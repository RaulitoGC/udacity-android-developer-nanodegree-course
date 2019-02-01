package com.rguzman.popularmovie.data.net.serializer;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import timber.log.Timber;

public class MovieTypeAdapterSerializer implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);

        final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);

        return new TypeAdapter<T>() {

            @Override
            public void write(JsonWriter out, T value) throws IOException {
                delegate.write(out, value);
            }

            @Override
            public T read(JsonReader in) throws IOException {

                final String BASE_REQUEST_IMAGE_URL = "http://image.tmdb.org/t/p/";
                final String REQUEST_IMAGE_SIZE_PATH = "w185";
                final String KEY_MOVIE_POSTER_PATH = "poster_path";

                JsonElement jsonElement = elementAdapter.read(in);
                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();

                    if (jsonObject.has("results") && jsonObject.get("results").isJsonArray() &&
                            !jsonObject.has("id")) {

                        JsonArray moviesJsonArrays = jsonObject.getAsJsonArray("results");
                        int size = moviesJsonArrays.size();
                        for (int i = 0; i < size; i++) {
                            JsonObject movieJsonObject = moviesJsonArrays.get(i).getAsJsonObject();
                            if (movieJsonObject.has("poster_path")) {

                                String path = movieJsonObject.get(KEY_MOVIE_POSTER_PATH).getAsString();

                                Uri builtUri = Uri.parse(BASE_REQUEST_IMAGE_URL).buildUpon()
                                        .appendPath(REQUEST_IMAGE_SIZE_PATH)
                                        .appendEncodedPath(path)
                                        .build();
                                movieJsonObject.addProperty("poster_path", builtUri.toString());
                            }

                            if (movieJsonObject.has("id")) {
                                int id = movieJsonObject.get("id").getAsInt();
                                movieJsonObject.addProperty("movieId", id);
                                movieJsonObject.remove("id");
                            }
                        }
                    }
                }

                return delegate.fromJsonTree(jsonElement);
            }
        }.nullSafe();
    }
}
