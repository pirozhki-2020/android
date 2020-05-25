package com.pirozhki.alcohall.recipes.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.pirozhki.alcohall.recipes.network.LikeApiResponse;
import com.pirozhki.alcohall.recipes.network.OneRecipeApiResponse;
import com.pirozhki.alcohall.recipes.network.RecipeApiRepository;
import com.pirozhki.alcohall.recipes.network.RecipeApiRepositoryImpl;
import com.pirozhki.alcohall.recipes.network.RecipesApiResponse;

import java.util.ArrayList;


public class RecipeViewModel extends ViewModel {
    private MediatorLiveData<RecipesApiResponse> mRecipesApiResponse;
    private RecipeApiRepository mRecipeApiRepository;
    private MediatorLiveData<OneRecipeApiResponse> mOneRecipeApiResponse;
    private MediatorLiveData<LikeApiResponse> mLikeApiResponse;

    public RecipeViewModel() {
        mRecipesApiResponse = new MediatorLiveData<>();
        mRecipeApiRepository = new RecipeApiRepositoryImpl();
        mOneRecipeApiResponse = new MediatorLiveData<>();
        mLikeApiResponse = new MediatorLiveData<>();
    }

    @NonNull
    public LiveData<RecipesApiResponse> getRecipesApiResponse() {
        return mRecipesApiResponse;
    }

    public LiveData<RecipesApiResponse> findRecipes(ArrayList<Integer> ids) {
        mRecipesApiResponse.addSource(
                mRecipeApiRepository.findRecipes(ids),
                apiResponse -> mRecipesApiResponse.setValue(apiResponse)
        );
        return mRecipesApiResponse;
    }

    @NonNull
    public LiveData<OneRecipeApiResponse> getOneRecipeApiResponse() {
        return mOneRecipeApiResponse;
    }

    public LiveData<OneRecipeApiResponse> findOneRecipe(@NonNull String id) {
        mOneRecipeApiResponse.addSource(
                mRecipeApiRepository.findOneRecipe(id),
                apiResponse -> mOneRecipeApiResponse.setValue(apiResponse)
        );
        return mOneRecipeApiResponse;
    }

    @NonNull
    public LiveData<LikeApiResponse> getLikeApiResponse() {
        return mLikeApiResponse;
    }

    public LiveData<LikeApiResponse> like(Integer id) {
        mLikeApiResponse.addSource(
                mRecipeApiRepository.like(id),
                apiResponse -> mLikeApiResponse.setValue(apiResponse)
        );
        return mLikeApiResponse;
    }
}
