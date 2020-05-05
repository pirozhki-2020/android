package com.pirozhki.alcohall.recipes.model;

import com.pirozhki.alcohall.ingredients.model.Ingredient;
import com.squareup.moshi.Json;

public class FullIngredient extends Ingredient {
    @Json(name = "volume")
    private Integer mVolume;

    public Integer getVolume() {
        return mVolume;
    }

    public void setVolume(Integer volume) {
        mVolume = volume;
    }
}
