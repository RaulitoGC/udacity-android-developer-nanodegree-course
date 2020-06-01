package com.rguzman.techstore.data.net;

import com.rguzman.techstore.data.net.request.LoginBody;
import com.rguzman.techstore.data.net.response.ProductResponse;
import com.rguzman.techstore.domain.model.Category;
import com.rguzman.techstore.domain.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    String SERVICE_ENDPOINT = "https://private-a5040-technologystoreapi.apiary-mock.com/";

    @POST("login")
    Call<User> login(@Body LoginBody loginBody);

    @GET("category")
    Call<List<Category>> loadCategories(@Header("Authorization") String token);

    @GET("category/{categoryId}")
    Call<List<ProductResponse>> loadProducts(@Header("Authorization") String token, @Path("categoryId") String categoryId);


}
