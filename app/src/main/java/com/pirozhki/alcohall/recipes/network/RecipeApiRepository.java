package com.pirozhki.alcohall.recipes.network;

import androidx.lifecycle.LiveData;

public interface RecipeApiRepository {
    LiveData<RecipeApiResponse> findRecipes(int[] ids);

    LiveData<OneRecipeResponse> findOneRecipe(String id);
}
