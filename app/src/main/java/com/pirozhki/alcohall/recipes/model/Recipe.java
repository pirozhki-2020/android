package com.pirozhki.alcohall.recipes.model;

import com.squareup.moshi.Json;

public class Recipe {
    @Json(name = "name")
    private String mName;
    @Json(name = "id")
    private Integer mId;

    public Integer getId() {
        return mId;
    }

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
