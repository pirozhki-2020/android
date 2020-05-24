package com.pirozhki.alcohall.ingredients.network;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pirozhki.alcohall.common.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class IngredientApiRepositoryImpl implements IngredientApiRepository {
    private IngredientApi mIngredientApi;

    public IngredientApiRepositoryImpl() {
        mIngredientApi = RetrofitInstance.getInstance().create(IngredientApi.class);
    }

    @Override
    public LiveData<IngredientApiResponse> findIngredients(String query) {
        final MutableLiveData<IngredientApiResponse> liveData = new MutableLiveData<>();
        Call<IngredientApi.Ingredients> call = mIngredientApi.getIngredients(query);
        call.enqueue(new Callback<IngredientApi.Ingredients>() {
            @Override
            public void onResponse(@NonNull Call<IngredientApi.Ingredients> call, @NonNull Response<IngredientApi.Ingredients> response) {
                if (response.body() == null)
                    liveData.setValue(new IngredientApiResponse(new HttpException(response)));
                else
                    liveData.setValue(new IngredientApiResponse(response.body().data.ingredients));
            }

            @Override
            public void onFailure(@NonNull Call<IngredientApi.Ingredients> call, @NonNull Throwable t) {
                liveData.setValue(new IngredientApiResponse(t));
            }
        });
        return liveData;
    }
}
