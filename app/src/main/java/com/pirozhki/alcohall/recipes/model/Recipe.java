package com.pirozhki.alcohall.recipes.model;

import com.pirozhki.alcohall.ingredients.model.Ingredient;
import com.squareup.moshi.Json;

import java.util.ArrayList;

public class Recipe {
    @Json(name = "name")
    public String name;

    private String mTitle;
    private ArrayList<Ingredient> mIngredients;
    private String mDescription;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public ArrayList<Ingredient> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        mIngredients = ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        mIngredients.add(ingredient);
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}
