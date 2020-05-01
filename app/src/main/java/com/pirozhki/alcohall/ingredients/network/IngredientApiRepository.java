package com.pirozhki.alcohall.ingredients.network;

import androidx.lifecycle.LiveData;

public interface IngredientApiRepository {
    LiveData<IngredientApiResponse> findIngredients(String query);
}
