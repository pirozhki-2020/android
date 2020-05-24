package com.pirozhki.alcohall.auth.network;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pirozhki.alcohall.common.RetrofitInstance;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthApiRepositoryImpl implements AuthApiRepository {
    private AuthApi mAuthApi;

    public AuthApiRepositoryImpl() {
        mAuthApi = RetrofitInstance.getInstance().create(AuthApi.class);
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
                } else {
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
                } else {
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

