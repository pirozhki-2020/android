package com.pirozhki.alcohall.recipes.network;

import com.pirozhki.alcohall.recipes.model.Recipe;
import com.squareup.moshi.Json;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RecipeApi {
    class RecipesData {
        @Json(name = "cocktails")
        public List<Recipe> cocktails;
    }
    class Recipes {
        @Json(name = "status")
        public String status;
        @Json(name = "data")
        public RecipeApi.RecipesData data;
    }

    @GET("/cocktails.list_cocktails")
    Call<RecipeApi.Recipes> getRecipes(@QueryMap Map<String, String> params);
}
