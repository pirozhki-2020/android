package com.pirozhki.alcohall.recipes.network;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface RecipeApiRepository {
    LiveData<RecipeApiResponse> findRecipes( int[] ids);
}
