package com.pirozhki.alcohall.ingredients.model;

import com.squareup.moshi.Json;

public class Ingredient {
    @Json(name = "id")
    public Integer id;
    @Json(name = "name")
    public String name;
}
