package com.pirozhki.alcohall.user.network;

import androidx.lifecycle.LiveData;

public interface UserApiRepository {
    LiveData<UserApiResponse> register(String email, String password);

    LiveData<UserApiResponse> login(String email, String password);

    LiveData<UserApiResponse> getUser();
}
