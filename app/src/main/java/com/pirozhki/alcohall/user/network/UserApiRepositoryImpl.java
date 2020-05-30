package com.pirozhki.alcohall.user.network;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pirozhki.alcohall.common.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class UserApiRepositoryImpl implements UserApiRepository {
    private UserApi mUserApi;

    public UserApiRepositoryImpl() {
        mUserApi = RetrofitInstance.getInstance().create(UserApi.class);
    }

    @Override
    public LiveData<UserApiResponse> register(String email, String password) {
        final MutableLiveData<UserApiResponse> liveData = new MutableLiveData<>();
        UserApi.AuthBody body = new UserApi.AuthBody(email, password);

        Call<UserApi.UserAnswer> call = mUserApi.regUser(body);
        call.enqueue(new Callback<UserApi.UserAnswer>() {
            @Override
            public void onResponse(@NonNull Call<UserApi.UserAnswer> call, @NonNull Response<UserApi.UserAnswer> response) {
                if (response.body() == null) {
                    liveData.setValue(new UserApiResponse(new HttpException(response)));
                } else if (response.code() == 200) {
                    liveData.setValue(new UserApiResponse(response.body().data, response.code()));
                } else {
                    liveData.setValue(new UserApiResponse(new Throwable(response.message())));
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserApi.UserAnswer> call, @NonNull Throwable t) {
                System.out.println(" FAIL FAIL FAIL");
                liveData.setValue(new UserApiResponse(t));
            }
        });

        return liveData;
    }

    @Override
    public LiveData<UserApiResponse> login(String email, String password) {
        final MutableLiveData<UserApiResponse> liveData = new MutableLiveData<>();
        UserApi.AuthBody body = new UserApi.AuthBody(email, password);
        Call<UserApi.UserAnswer> call = mUserApi.loginUser(body);

        call.enqueue(new Callback<UserApi.UserAnswer>() {
            @Override
            public void onResponse(@NonNull Call<UserApi.UserAnswer> call, @NonNull Response<UserApi.UserAnswer> response) {
                if (response.body() == null) {
                    liveData.setValue(new UserApiResponse(new HttpException(response)));
                } else if (response.code() == 200) {
                    liveData.setValue(new UserApiResponse(response.body().data, response.code()));
                } else {
                    liveData.setValue(new UserApiResponse(new Throwable(response.message())));
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserApi.UserAnswer> call, @NonNull Throwable t) {
                liveData.setValue(new UserApiResponse(t));
            }
        });

        return liveData;
    }

    @Override
    public LiveData<UserApiResponse> getUser() {
        final MutableLiveData<UserApiResponse> liveData = new MutableLiveData<>();
        Call<UserApi.OneUser> call = mUserApi.getUser();

        call.enqueue(new Callback<UserApi.OneUser>() {
            @Override
            public void onResponse(@NonNull Call<UserApi.OneUser> call, @NonNull Response<UserApi.OneUser> response) {
                if (response.body() == null) {
                    liveData.setValue(new UserApiResponse(new HttpException(response)));
                } else {
                    liveData.setValue(new UserApiResponse(response.body().data, response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserApi.OneUser> call, @NonNull Throwable t) {
                liveData.setValue(new UserApiResponse(t));
            }
        });

        return liveData;
    }

    @Override
    public LiveData<UserApiResponse> logout() {
        final MutableLiveData<UserApiResponse> liveData = new MutableLiveData<>();
        Call<UserApi.UserAnswer> call = mUserApi.logoutUser();

        call.enqueue(new Callback<UserApi.UserAnswer>() {
            @Override
            public void onResponse(@NonNull Call<UserApi.UserAnswer> call, @NonNull Response<UserApi.UserAnswer> response) {
                if (response.body() == null) {
                    liveData.setValue(new UserApiResponse(new HttpException(response)));
                } else {
                    liveData.setValue(new UserApiResponse(response.body().data, response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserApi.UserAnswer> call, @NonNull Throwable t) {
                liveData.setValue(new UserApiResponse(t));
            }
        });

        return liveData;
    }
}
