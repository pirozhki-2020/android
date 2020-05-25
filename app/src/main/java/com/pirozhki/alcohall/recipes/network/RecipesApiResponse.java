package com.pirozhki.alcohall.recipes.network;

import com.pirozhki.alcohall.recipes.model.Recipe;

import java.util.List;

public class RecipesApiResponse {
    private List<Recipe> mRecipes;
    private Throwable mError;

    RecipesApiResponse(List<Recipe> cocktails) {
        mRecipes = cocktails;
        mError = null;
    }

    RecipesApiResponse(Throwable error) {
        mError = error;
        mRecipes = null;
    }

    public List<Recipe> getRecipes() {
        return mRecipes;
    }

    public Throwable getError() {
        return mError;
    }
}
