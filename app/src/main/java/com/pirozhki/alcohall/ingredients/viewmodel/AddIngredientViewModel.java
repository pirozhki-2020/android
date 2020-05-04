package com.pirozhki.alcohall.ingredients.viewmodel;

import com.pirozhki.alcohall.ingredients.network.IngredientApiRepository;
import com.pirozhki.alcohall.ingredients.network.IngredientApiRepositoryImpl;
import com.pirozhki.alcohall.ingredients.network.IngredientApiResponse;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

public class AddIngredientViewModel extends ViewModel {
    private MediatorLiveData<IngredientApiResponse> mIngredientApiResponse;
    private IngredientApiRepository mIngredientApiRepository;

    public AddIngredientViewModel() {
        mIngredientApiResponse = new MediatorLiveData<>();
        mIngredientApiRepository = new IngredientApiRepositoryImpl();
    }

    @NonNull
    public LiveData<IngredientApiResponse> getApiResponse() {
        return mIngredientApiResponse;
    }

    public LiveData<IngredientApiResponse> findIngredients(@NonNull String query) {
        mIngredientApiResponse.addSource(
                mIngredientApiRepository.findIngredients(query),
                apiResponse -> mIngredientApiResponse.setValue(apiResponse)
        );
        return mIngredientApiResponse;
    }
}
