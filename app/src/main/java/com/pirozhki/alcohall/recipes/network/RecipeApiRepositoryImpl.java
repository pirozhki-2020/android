package com.pirozhki.alcohall.recipes.network;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pirozhki.alcohall.common.RetrofitInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class RecipeApiRepositoryImpl implements RecipeApiRepository {

    private RecipeApi mRecipeApi;

    public RecipeApiRepositoryImpl() {
        mRecipeApi = RetrofitInstance.getInstance().create(RecipeApi.class);
    }

    @Override
    public LiveData<RecipeApiResponse> findRecipes(ArrayList<Integer> ids) {
        final MutableLiveData<RecipeApiResponse> liveData = new MutableLiveData<>();
        Map<String, String> params = new HashMap<String, String>();
        for (int id : ids) {
            params.put("ingredient", Integer.toString(id));
        }
        Call<RecipeApi.Recipes> call = mRecipeApi.getRecipes(params);
        call.enqueue(new Callback<RecipeApi.Recipes>() {
            @Override
            public void onResponse(@NonNull Call<RecipeApi.Recipes> call, @NonNull Response<RecipeApi.Recipes> response) {
                if (response.body() == null)
                    liveData.setValue(new RecipeApiResponse(new HttpException(response)));
                else
                    liveData.setValue(new RecipeApiResponse(response.body().data.cocktails));
            }

            @Override
            public void onFailure(@NonNull Call<RecipeApi.Recipes> call, @NonNull Throwable t) {
                liveData.setValue(new RecipeApiResponse(t));
            }
        });
        return liveData;
    }

    @Override
    public LiveData<OneRecipeResponse> findOneRecipe(String id) {
        final MutableLiveData<OneRecipeResponse> liveData = new MutableLiveData<>();
        Call<RecipeApi.OneRecipe> call = mRecipeApi.getCocktail(id);
        call.enqueue(new Callback<RecipeApi.OneRecipe>() {
            @Override
            public void onResponse(@NonNull Call<RecipeApi.OneRecipe> call, @NonNull Response<RecipeApi.OneRecipe> response) {
                if (response.body() == null)
                    liveData.setValue(new OneRecipeResponse(new HttpException(response)));
                else
                    liveData.setValue(new OneRecipeResponse(Objects.requireNonNull(response.body()).data));
            }

            @Override
            public void onFailure(@NonNull Call<RecipeApi.OneRecipe> call, @NonNull Throwable t) {
                liveData.setValue(new OneRecipeResponse(t));
            }
        });
        return liveData;
    }
}
