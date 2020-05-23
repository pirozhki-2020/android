package com.pirozhki.alcohall.auth.network;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pirozhki.alcohall.auth.model.User;

import java.util.Objects;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class AuthApiRepositoryImpl implements AuthApiRepository {
    private static final String HOST = "alcohall.space";

    private AuthApi mAuthApi;

    public AuthApiRepositoryImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(new HttpUrl.Builder()
                        .scheme("https")
                        .host(HOST)
                        .build())
                .build();

        mAuthApi = retrofit.create(AuthApi.class);
    }

    @Override
    public LiveData<AuthApiResponse> register(String email, String password) {
        final MutableLiveData<AuthApiResponse> liveData = new MutableLiveData<>();
        AuthApi.AuthBody body = new AuthApi.AuthBody(email, password);
        System.out.println(" in repo  ");
        System.out.println(email);
        Call<User> call = mAuthApi.regUser(body);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                System.out.println(response.body().getEmail());
                liveData.setValue(new AuthApiResponse(Objects.requireNonNull(response.body())));
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                System.out.println(" FAIL FAIL FAIL");
                liveData.setValue(new AuthApiResponse(t));
            }
        });
        return liveData;
    }

    @Override
    public LiveData<AuthApiResponse> login(String email, String password) {
        final MutableLiveData<AuthApiResponse> liveData = new MutableLiveData<>();
        AuthApi.AuthBody body = new AuthApi.AuthBody(email, password);
        Call<User> call = mAuthApi.loginUser(body);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                liveData.setValue(new AuthApiResponse(Objects.requireNonNull(response.body())));
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                liveData.setValue(new AuthApiResponse(t));
            }
        });
        return liveData;

    }
}

