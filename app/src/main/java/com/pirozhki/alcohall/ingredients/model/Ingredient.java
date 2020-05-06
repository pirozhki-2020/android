package com.pirozhki.alcohall.ingredients.model;

import com.squareup.moshi.Json;

public class Ingredient {
    @Json(name = "id")
    private Integer mId;
    @Json(name = "name")
    private String mName;

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
