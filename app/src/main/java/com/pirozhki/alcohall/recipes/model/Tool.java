package com.pirozhki.alcohall.recipes.model;

import com.squareup.moshi.Json;

public class Tool {
    @Json(name = "number")
    private Integer mNumber;

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

    public Integer getNumber() {
        return mNumber;
    }

    public void setNumber(Integer number) {
        mNumber = number;
    }
}
