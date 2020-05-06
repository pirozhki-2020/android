package com.pirozhki.alcohall.recipes.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.pirozhki.alcohall.recipes.network.OneRecipeResponse;
import com.pirozhki.alcohall.recipes.network.RecipeApi;
import com.pirozhki.alcohall.recipes.network.RecipeApiRepository;
import com.pirozhki.alcohall.recipes.network.RecipeApiRepositoryImpl;
import com.pirozhki.alcohall.recipes.network.RecipeApiResponse;


public class RecipeViewModel extends ViewModel {
    private MediatorLiveData<RecipeApiResponse> mRecipeApiResponse;
    private RecipeApiRepository mRecipeApiRepository;
    private MediatorLiveData<OneRecipeResponse> mOneRecipeResponse;

    public RecipeViewModel() {
        mRecipeApiResponse = new MediatorLiveData<>();
        mRecipeApiRepository = new RecipeApiRepositoryImpl();
        mOneRecipeResponse = new MediatorLiveData<>();
    }

    @NonNull
    public LiveData<RecipeApiResponse> getApiResponse() {
        return mRecipeApiResponse;
    }

    public LiveData<RecipeApiResponse> findRecipes(@NonNull int[] ids) {
        mRecipeApiResponse.addSource(
                mRecipeApiRepository.findRecipes(ids),
                apiResponse -> mRecipeApiResponse.setValue(apiResponse)
        );
        return mRecipeApiResponse;
    }

    @NonNull
    public LiveData<OneRecipeResponse> getOneRecipeApiResponse() {
        return mOneRecipeResponse;
    }

    public  LiveData<OneRecipeResponse> findOneRecipe(@NonNull String id) {
        mOneRecipeResponse.addSource(
                mRecipeApiRepository.findOneRecipe(id),
                apiResponse -> mOneRecipeResponse.setValue(apiResponse)
        );
        return mOneRecipeResponse;
    }
}
