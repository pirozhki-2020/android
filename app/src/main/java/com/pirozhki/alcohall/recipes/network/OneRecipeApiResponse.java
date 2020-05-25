package com.pirozhki.alcohall.recipes.network;

import com.pirozhki.alcohall.recipes.model.FullRecipe;
import com.pirozhki.alcohall.recipes.model.Recipe;

import java.util.List;

public class OneRecipeApiResponse {
    private FullRecipe mRecipe;
    private Throwable mError;

    OneRecipeApiResponse(FullRecipe recipe) {
        mRecipe = recipe;
        mError = null;
    }

    OneRecipeApiResponse(Throwable error) {
        mError = error;
        mRecipe = null;
    }

    public FullRecipe getRecipe() {
        return mRecipe;
    }

    public Throwable getError() {
        return mError;
    }
}
