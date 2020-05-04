package com.pirozhki.alcohall.recipes.network;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RecipeApiRepositoryImpl implements RecipeApiRepository {
    private static final String HOST = "alcohall.space";

    private RecipeApi mRecipeApi;

    public RecipeApiRepositoryImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(new HttpUrl.Builder()
                        .scheme("https")
                        .host(HOST)
                        .build())
                .build();

        mRecipeApi = retrofit.create(RecipeApi.class);
    }

    @Override
    public LiveData<RecipeApiResponse> findRecipes(int[] ids){
        final MutableLiveData<RecipeApiResponse> liveData = new MutableLiveData<>();
        Map<String, String> params = new HashMap<String, String>();
        for (int id: ids) {
            params.put("ingredient", Integer.toString(id));
            System.out.println( Integer.toString(id));
        }
        Call<RecipeApi.Recipes> call = mRecipeApi.getRecipes(params);
        call.enqueue(new Callback<RecipeApi.Recipes>() {
            @Override
            public void onResponse(@NonNull Call<RecipeApi.Recipes> call, @NonNull Response<RecipeApi.Recipes> response) {
                liveData.setValue(new RecipeApiResponse(Objects.requireNonNull(response.body()).data.cocktails));
            }

            @Override
            public void onFailure(@NonNull Call<RecipeApi.Recipes> call, @NonNull Throwable t) {
                liveData.setValue(new RecipeApiResponse(t));
            }
        });
        return liveData;
    }
}
