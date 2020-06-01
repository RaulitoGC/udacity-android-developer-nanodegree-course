package com.rguzman.techstore.data.net.serializer;

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

public class ProductTypeAdapterSerializer implements TypeAdapterFactory {
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

        JsonElement jsonElement = elementAdapter.read(in);
        if (jsonElement.isJsonArray()) {
          JsonArray productJsonArray = jsonElement.getAsJsonArray();

          int size = productJsonArray.size();
          for (int i = 0; i < size; i++) {
            JsonObject productJsonObject = productJsonArray.get(i).getAsJsonObject();
            if (productJsonObject.has("productId")) {
              String productId = productJsonObject.get("productId").getAsString();
              if (productJsonObject.has("features")) {
                JsonArray featureJsonArray = productJsonObject.get("features").getAsJsonArray();
                int featureSize = featureJsonArray.size();
                for (int j = 0; j < featureSize; j++) {
                  JsonObject featureJsonObject = featureJsonArray.get(j).getAsJsonObject();
                  featureJsonObject.addProperty("productId", productId);
                }
              }
            }
          }
        }

        return delegate.fromJsonTree(jsonElement);
      }
    }.nullSafe();
  }
}
