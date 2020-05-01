package com.pirozhki.alcohall.ingredients.network;

import com.pirozhki.alcohall.ingredients.model.Ingredient;

import java.util.List;

public class IngredientApiResponse {
    private List<Ingredient> mIngredients;
    private Throwable mError;

    public IngredientApiResponse(List<Ingredient> ingredients) {
        mIngredients = ingredients;
        mError = null;
    }

    public IngredientApiResponse(Throwable error) {
        mError = error;
        mIngredients = null;
    }

    public List<Ingredient> getIngredients() {
        return mIngredients;
    }

    public Throwable getError() {
        return mError;
    }
}
