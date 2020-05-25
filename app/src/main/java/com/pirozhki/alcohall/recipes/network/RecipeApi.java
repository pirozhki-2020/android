package com.pirozhki.alcohall.recipes.network;

import com.pirozhki.alcohall.recipes.model.FullRecipe;
import com.pirozhki.alcohall.recipes.model.Recipe;
import com.squareup.moshi.Json;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
        public RecipesData data;
    }

    @GET("/cocktails.list_cocktails")
    Call<RecipeApi.Recipes> getRecipes(@QueryMap Map<String, String> params);

    class OneRecipe {
        @Json(name = "status")
        public String status;
        @Json(name = "data")
        public FullRecipe data;
    }

    @GET("/cocktails.get_cocktail")
    Call<OneRecipe> getCocktail(@Query("id") String id);

    class IsLiked {
        @Json(name = "liked")
        public Boolean liked;
    }

    class Like {
        @Json(name = "status")
        public String status;
        @Json(name = "data")
        public IsLiked data;
    }

    class LikeBody {
        @Json(name = "cocktail")
        public Integer id;
    }

    @POST("/cocktails.like")
    Call<Like> likeCocktail(@Body LikeBody body);
}
