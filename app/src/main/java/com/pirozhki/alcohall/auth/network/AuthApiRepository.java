package com.pirozhki.alcohall.auth.network;

import androidx.lifecycle.LiveData;

public interface AuthApiRepository {
    LiveData<AuthApiResponse> register(String email, String password);

    LiveData<AuthApiResponse> login(String email, String password);
}
