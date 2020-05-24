package com.pirozhki.alcohall.recipes.network;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;

public interface RecipeApiRepository {
    LiveData<RecipeApiResponse> findRecipes(ArrayList<Integer> ids);

    LiveData<OneRecipeResponse> findOneRecipe(String id);
}
