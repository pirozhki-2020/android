package com.pirozhki.alcohall.recipes.network;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pirozhki.alcohall.common.RetrofitInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    public LiveData<RecipesApiResponse> findRecipes(ArrayList<Integer> ids) {
        final MutableLiveData<RecipesApiResponse> liveData = new MutableLiveData<>();
        Map<String, String> params = new HashMap<String, String>();
        for (int id : ids) {
            params.put("ingredient", Integer.toString(id));
        }

        Call<RecipeApi.Recipes> call = mRecipeApi.getRecipes(params);
        call.enqueue(new Callback<RecipeApi.Recipes>() {
            @Override
            public void onResponse(@NonNull Call<RecipeApi.Recipes> call, @NonNull Response<RecipeApi.Recipes> response) {
                if (response.body() == null)
                    liveData.setValue(new RecipesApiResponse(new HttpException(response)));
                else
                    liveData.setValue(new RecipesApiResponse(response.body().data.cocktails));
            }

            @Override
            public void onFailure(@NonNull Call<RecipeApi.Recipes> call, @NonNull Throwable t) {
                liveData.setValue(new RecipesApiResponse(t));
            }
        });

        return liveData;
    }

    @Override
    public LiveData<OneRecipeApiResponse> findOneRecipe(String id) {
        MutableLiveData<OneRecipeApiResponse> oneRecipeLiveData = new MutableLiveData<>();

        Call<RecipeApi.OneRecipe> call = mRecipeApi.getCocktail(id);
        call.enqueue(new Callback<RecipeApi.OneRecipe>() {
            @Override
            public void onResponse(@NonNull Call<RecipeApi.OneRecipe> call, @NonNull Response<RecipeApi.OneRecipe> response) {
                System.out.println(response);
                if (response.body() == null) {
                    oneRecipeLiveData.setValue(new OneRecipeApiResponse(new HttpException(response)));
                } else {
                    System.out.println(response.body().data.getIsLiked());
                    oneRecipeLiveData.setValue(new OneRecipeApiResponse(response.body().data));
                }
            }

            @Override
            public void onFailure(@NonNull Call<RecipeApi.OneRecipe> call, @NonNull Throwable t) {
                oneRecipeLiveData.setValue(new OneRecipeApiResponse(t));
            }
        });

        return oneRecipeLiveData;
    }

    @Override
    public LiveData<LikeApiResponse> like(Integer id) {
        RecipeApi.LikeBody body = new RecipeApi.LikeBody();
        body.id = id;
        MutableLiveData<LikeApiResponse> likeLiveData = new MutableLiveData<>();

        Call<RecipeApi.Like> call = mRecipeApi.likeCocktail(body);
        call.enqueue(new Callback<RecipeApi.Like>() {
            @Override
            public void onResponse(@NonNull Call<RecipeApi.Like> call, @NonNull Response<RecipeApi.Like> response) {
                if (response.body() == null) {
                    likeLiveData.setValue(new LikeApiResponse(new HttpException(response)));
                } else if (response.code() == 200) {
                    likeLiveData.setValue(new LikeApiResponse(response.body().data));
                } else {
                    likeLiveData.setValue(new LikeApiResponse(new Throwable(response.message())));
                }
            }

            @Override
            public void onFailure(@NonNull Call<RecipeApi.Like> call, @NonNull Throwable t) {
                likeLiveData.setValue(new LikeApiResponse(t));
            }
        });

        return likeLiveData;
    }
}
