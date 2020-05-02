package com.pirozhki.alcohall.recipes.network;

import com.pirozhki.alcohall.recipes.model.Recipe;
import com.squareup.moshi.Json;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeApi {
    class RecipesData {
        @Json(name = "recipes")
        public List<Recipe> recipes;
    }
    class Recipes {
        @Json(name = "status")
        public String status;
        @Json(name = "data")
        public RecipeApi.RecipesData data;
    }

    @GET("/cocktails.list_ingredients")
    Call<RecipeApi.Recipes> getRecipes(@Query("query") String query);
}
