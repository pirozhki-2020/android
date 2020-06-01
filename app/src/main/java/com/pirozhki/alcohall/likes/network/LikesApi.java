package com.pirozhki.alcohall.likes.network;

import com.pirozhki.alcohall.recipes.model.Recipe;
import com.squareup.moshi.Json;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LikesApi {
    class LikesData {
        @Json(name = "cocktails")
        public List<Recipe> cocktails;
    }

    class Likes {
        @Json(name = "status")
        public String status;
        @Json(name = "data")
        public LikesData data;
    }

    @GET("user.liked")
    Call<Likes> getRecipes();
}
