package com.pirozhki.alcohall.recipes.model;

import com.pirozhki.alcohall.ingredients.model.Ingredient;
import com.squareup.moshi.Json;

import java.util.ArrayList;

public class Recipe {
    @Json(name = "name")
    public String mName;
    @Json(name = "id")
    private Integer mId;

    public Integer getId() {return mId;}

    public void setId(Integer id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
