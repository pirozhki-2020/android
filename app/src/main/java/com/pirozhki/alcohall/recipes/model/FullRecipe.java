package com.pirozhki.alcohall.recipes.model;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import com.pirozhki.alcohall.R;
import com.pirozhki.alcohall.common.RetrofitInstance;
import com.squareup.moshi.Json;

import java.util.List;

public class FullRecipe extends Recipe {
    @Json(name = "description")
    private String mDescription;

    @Json(name = "tools")
    private List<Tool> mTools;

    @Json(name = "steps")
    private List<String> mSteps;

    @Json(name = "ingredients")
    private List<FullIngredient> mIngredients;

    @Json(name = "image_link")
    private String mImageLink;

    @Json(name = "is_liked")
    private Boolean  mIsLiked;

    public List<String> getSteps() {
        return mSteps;
    }

    public void setSteps(List<String> Steps) {
        mSteps = Steps;
    }

    public List<FullIngredient> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(List<FullIngredient> ingredients) {
        mIngredients = ingredients;
    }

    public Uri getImageLink() {
        return Uri.parse(RetrofitInstance.HOST_URL + mImageLink);
    }

    public void setImageLink(String imageLink) {
        mImageLink = imageLink;
    }

    public List<Tool> getTools() {
        return mTools;
    }

    public void setTools(List<Tool> tools) {
        mTools = tools;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Boolean getIsLiked() {
        return mIsLiked;
    }

    public void setIsLiked(Boolean mIsLiked) {
        this.mIsLiked = mIsLiked;
    }
}
