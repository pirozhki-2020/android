package com.pirozhki.alcohall.ingredients.network;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Objects;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class IngredientApiRepositoryImpl implements IngredientApiRepository {
    private static final String HOST = "alcohall.space";

    private IngredientApi mIngredientApi;

    public IngredientApiRepositoryImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(new HttpUrl.Builder()
                        .scheme("http")
                        .host(HOST)
                        .build())
                .build();

        mIngredientApi = retrofit.create(IngredientApi.class);
    }

    @Override
    public LiveData<IngredientApiResponse> findIngredients(String query) {
        final MutableLiveData<IngredientApiResponse> liveData = new MutableLiveData<>();
        Call<IngredientApi.Ingredients> call = mIngredientApi.getIngredients(query);
        call.enqueue(new Callback<IngredientApi.Ingredients>() {
            @Override
            public void onResponse(@NonNull Call<IngredientApi.Ingredients> call, @NonNull Response<IngredientApi.Ingredients> response) {
                liveData.setValue(new IngredientApiResponse(Objects.requireNonNull(response.body()).data.ingredients));
            }

            @Override
            public void onFailure(@NonNull Call<IngredientApi.Ingredients> call, @NonNull Throwable t) {
                liveData.setValue(new IngredientApiResponse(t));
            }
        });
        return liveData;
    }
}
