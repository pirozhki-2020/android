package com.pirozhki.alcohall.recipes.network;

import com.pirozhki.alcohall.recipes.model.FullRecipe;
import com.pirozhki.alcohall.recipes.model.Recipe;

import java.util.List;

public class OneRecipeResponse {
    private FullRecipe mRecipe;
    private Throwable mError;

    OneRecipeResponse(FullRecipe recipe) {
        mRecipe = recipe;
        mError = null;
    }

    OneRecipeResponse(Throwable error) {
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
