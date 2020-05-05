package com.pirozhki.alcohall.recipes.model;

import com.pirozhki.alcohall.ingredients.model.Ingredient;
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

    public String getImageLink() {
        return mImageLink;
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
}
