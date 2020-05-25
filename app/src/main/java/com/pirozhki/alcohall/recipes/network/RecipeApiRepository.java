package com.pirozhki.alcohall.recipes.network;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;

public interface RecipeApiRepository {
    LiveData<RecipesApiResponse> findRecipes(ArrayList<Integer> ids);

    LiveData<OneRecipeApiResponse> findOneRecipe(String id);

    LiveData<LikeApiResponse> like(Integer id);
}
