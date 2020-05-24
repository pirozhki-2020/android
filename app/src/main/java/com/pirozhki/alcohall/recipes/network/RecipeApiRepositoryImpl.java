package com.pirozhki.alcohall.recipes.network;

import android.util.Log;

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
    final MutableLiveData<OneRecipeResponse> oneRecipeLiveData = new MutableLiveData<>();


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

        Call<RecipeApi.OneRecipe> call = mRecipeApi.getCocktail(id);
        call.enqueue(new Callback<RecipeApi.OneRecipe>() {
            @Override
            public void onResponse(@NonNull Call<RecipeApi.OneRecipe> call, @NonNull Response<RecipeApi.OneRecipe> response) {
                System.out.println(response);
                if (response.body() == null)
                    oneRecipeLiveData.setValue(new OneRecipeResponse(new HttpException(response)));
                else
                    oneRecipeLiveData.setValue(new OneRecipeResponse(Objects.requireNonNull(response.body()).data));
            }

            @Override
            public void onFailure(@NonNull Call<RecipeApi.OneRecipe> call, @NonNull Throwable t) {
                oneRecipeLiveData.setValue(new OneRecipeResponse(t));
            }
        });
        return oneRecipeLiveData;
    }

    @Override
    public  void like (Integer id) {
        RecipeApi.LikeBody body  = new RecipeApi.LikeBody();
        body.id = id;
        Call<RecipeApi.Like> call = mRecipeApi.likeCocktail(body);

        call.enqueue(new Callback<RecipeApi.Like>() {
            @Override
            public void onResponse(@NonNull Call<RecipeApi.Like> call, @NonNull Response<RecipeApi.Like> response) {
                System.out.println(response);
                if (response.body() != null && response.body().status.equals("ok")) {
                    findOneRecipe(id.toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<RecipeApi.Like> call, @NonNull Throwable t) {
                Log.d("Test", "Failed to like ", t);
            }
        });

    }
}
