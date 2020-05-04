package com.pirozhki.alcohall.recipes.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.pirozhki.alcohall.recipes.network.RecipeApiRepository;
import com.pirozhki.alcohall.recipes.network.RecipeApiRepositoryImpl;
import com.pirozhki.alcohall.recipes.network.RecipeApiResponse;

import java.util.List;


public class RecipeViewModel extends ViewModel {
    private MediatorLiveData<RecipeApiResponse> mRecipeApiResponse;
    private RecipeApiRepository mRecipeApiRepository;

    public RecipeViewModel() {
        mRecipeApiResponse = new MediatorLiveData<>();
        mRecipeApiRepository = new RecipeApiRepositoryImpl();
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
}
