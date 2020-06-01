package com.pirozhki.alcohall.user.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.pirozhki.alcohall.user.model.User;
import com.pirozhki.alcohall.user.network.UserApiRepository;
import com.pirozhki.alcohall.user.network.UserApiRepositoryImpl;
import com.pirozhki.alcohall.user.network.UserApiResponse;

public class AuthViewModel extends ViewModel {
    private MediatorLiveData<UserApiResponse> mUserApiResponse;
    private UserApiRepository mUserApiRepository;
    private User mCachedUser = null;

    public AuthViewModel() {
        mUserApiResponse = new MediatorLiveData<>();
        mUserApiRepository = new UserApiRepositoryImpl();
    }

    @NonNull
    public LiveData<UserApiResponse> getApiResponse() {
        return mUserApiResponse;
    }

    public LiveData<UserApiResponse> register(@NonNull String email, String password) {
        mUserApiResponse.addSource(
                mUserApiRepository.register(email, password),
                apiResponse -> mUserApiResponse.setValue(apiResponse)
        );
        return mUserApiResponse;
    }

    public LiveData<UserApiResponse> login(@NonNull String email, String password) {
        mUserApiResponse.addSource(
                mUserApiRepository.login(email, password),
                apiResponse -> mUserApiResponse.setValue(apiResponse)
        );
        return mUserApiResponse;
    }

    public void cashUser(User user) {
        mCachedUser = user;
    }

    public boolean hasCash(User user) {
        return mCachedUser == user;
    }
}
