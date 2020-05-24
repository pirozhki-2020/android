package com.pirozhki.alcohall.selections.model;

import com.pirozhki.alcohall.recipes.model.Recipe;
import com.squareup.moshi.Json;

import java.util.List;

public class Selection {
    @Json(name = "name")
    private String mName;
    @Json(name = "id")
    private Integer mId;
    @Json(name = "description")
    private String mDescription;
    @Json(name = "cocktails")
    private List<Recipe> mRecipes;

    public Integer getId() {
        return mId;
    }

    public void setId(Integer mId) {
        this.mId = mId;
    }

    public List<Recipe> getRecipes() {
        return mRecipes;
    }

    public void setRecipes(List<Recipe> mRecipes) {
        this.mRecipes = mRecipes;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }
}
