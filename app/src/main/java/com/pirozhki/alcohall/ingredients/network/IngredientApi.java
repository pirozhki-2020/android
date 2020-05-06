package com.pirozhki.alcohall.ingredients.network;

import com.pirozhki.alcohall.ingredients.model.Ingredient;

import com.squareup.moshi.Json;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IngredientApi {
    class IngredientsData {
        @Json(name = "ingredients")
        public List<Ingredient> ingredients;
    }

    class Ingredients {
        @Json(name = "status")
        public String status;
        @Json(name = "data")
        public IngredientsData data;
    }

    @GET("/cocktails.list_ingredients")
    Call<Ingredients> getIngredients(@Query("query") String query);
}
