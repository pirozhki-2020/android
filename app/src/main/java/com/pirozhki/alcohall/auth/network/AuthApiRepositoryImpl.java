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

        Call<AuthApi.UserAnswer> call = mAuthApi.regUser(body);
        call.enqueue(new Callback<AuthApi.UserAnswer>() {
            @Override
            public void onResponse(@NonNull Call<AuthApi.UserAnswer> call, @NonNull Response<AuthApi.UserAnswer> response) {
                System.out.println(response);
                if (response.code() == 200) {
                    liveData.setValue(new AuthApiResponse(Objects.requireNonNull(response.body()).data));
                }
                else {
                    liveData.setValue(new AuthApiResponse(new Throwable(response.message())));
                }

            }

            @Override
            public void onFailure(@NonNull Call<AuthApi.UserAnswer> call, @NonNull Throwable t) {
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
        Call<AuthApi.UserAnswer> call = mAuthApi.loginUser(body);
        call.enqueue(new Callback<AuthApi.UserAnswer>() {
            @Override
            public void onResponse(@NonNull Call<AuthApi.UserAnswer> call, @NonNull Response<AuthApi.UserAnswer> response) {
                System.out.println(response);
                if (response.code() == 200) {
                    liveData.setValue(new AuthApiResponse(Objects.requireNonNull(response.body()).data));
                }
                else {
                    liveData.setValue(new AuthApiResponse(new Throwable(response.message())));
                }
            }

            @Override
            public void onFailure(@NonNull Call<AuthApi.UserAnswer> call, @NonNull Throwable t) {
                liveData.setValue(new AuthApiResponse(t));
            }
        });
        return liveData;

    }
}

