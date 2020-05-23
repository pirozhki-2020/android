package com.pirozhki.alcohall.auth.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.pirozhki.alcohall.auth.network.AuthApiRepository;
import com.pirozhki.alcohall.auth.network.AuthApiRepositoryImpl;
import com.pirozhki.alcohall.auth.network.AuthApiResponse;

public class AuthViewModel extends ViewModel {
    private MediatorLiveData<AuthApiResponse> mAuthApiResponse;
    private AuthApiRepository mAuthApiRepository;


    public AuthViewModel() {
        mAuthApiResponse = new MediatorLiveData<>();
        mAuthApiRepository = new AuthApiRepositoryImpl();
    }

    @NonNull
    public LiveData<AuthApiResponse> getApiResponse() {
        return mAuthApiResponse;
    }

    public LiveData<AuthApiResponse> register(@NonNull String email, String password) {
        System.out.println(" viewmodel register ");
        mAuthApiResponse.addSource(
                mAuthApiRepository.register(email, password),
                apiResponse -> mAuthApiResponse.setValue(apiResponse)
        );
        System.out.println(" go from viewmodel register");
        return mAuthApiResponse;
    }

    public LiveData<AuthApiResponse> login(@NonNull String email, String password) {
        mAuthApiResponse.addSource(
                mAuthApiRepository.login(email, password),
                apiResponse -> mAuthApiResponse.setValue(apiResponse)
        );
        return mAuthApiResponse;
    }

}
