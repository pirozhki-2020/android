package com.pirozhki.alcohall.likes.network;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pirozhki.alcohall.common.RetrofitInstance;
import com.pirozhki.alcohall.recipes.network.OneRecipeResponse;
import com.pirozhki.alcohall.recipes.network.RecipeApi;
import com.pirozhki.alcohall.recipes.network.RecipeApiResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class LikesApiRepoImpl implements LikesApiRepo {

    private LikesApi mLikesApi;


    public LikesApiRepoImpl() {
        mLikesApi = RetrofitInstance.getInstance().create(LikesApi.class);
    }

    @Override
    public LiveData<LikesApiResponse> findRecipes() {
        final MutableLiveData<LikesApiResponse> liveData = new MutableLiveData<>();
        Call<LikesApi.Likes> call = mLikesApi.getRecipes();
        call.enqueue(new Callback<LikesApi.Likes>() {
            @Override
            public void onResponse(@NonNull Call<LikesApi.Likes> call, @NonNull Response<LikesApi.Likes> response) {
                if (response.body() == null)
                    liveData.setValue(new LikesApiResponse(new HttpException(response)));
                else
                    liveData.setValue(new LikesApiResponse(response.body().data.cocktails));
            }

            @Override
            public void onFailure(@NonNull Call<LikesApi.Likes> call, @NonNull Throwable t) {
                liveData.setValue(new LikesApiResponse(t));
            }
        });
        return liveData;
    }

}
