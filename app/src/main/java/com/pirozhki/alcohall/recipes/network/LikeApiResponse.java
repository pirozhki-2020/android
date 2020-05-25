package com.pirozhki.alcohall.recipes.network;

public class LikeApiResponse {
    private RecipeApi.IsLiked mIsLiked;
    private Throwable mError;

    LikeApiResponse(RecipeApi.IsLiked isLiked) {
        mIsLiked = isLiked;
        mError = null;
    }

    LikeApiResponse(Throwable error) {
        mError = error;
        mIsLiked = null;
    }

    public RecipeApi.IsLiked getIsLiked() {
        return mIsLiked;
    }

    public Throwable getError() {
        return mError;
    }
}
