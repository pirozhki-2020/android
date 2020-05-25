package com.pirozhki.alcohall.likes.network;

import com.pirozhki.alcohall.recipes.model.Recipe;

import java.util.List;

public class LikesApiResponse {
    private List<Recipe> mRecipes;
    private Throwable mError;

    LikesApiResponse(List<Recipe> cocktails) {
        mRecipes = cocktails;
        mError = null;
    }

    LikesApiResponse(Throwable error) {
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
