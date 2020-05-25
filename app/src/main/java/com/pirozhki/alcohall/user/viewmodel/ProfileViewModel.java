package com.pirozhki.alcohall.user.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.pirozhki.alcohall.user.network.UserApiResponse;
import com.pirozhki.alcohall.user.network.UserApiRepository;
import com.pirozhki.alcohall.user.network.UserApiRepositoryImpl;

public class ProfileViewModel extends ViewModel {
    private MediatorLiveData<UserApiResponse> mUserApiResponse;
    private UserApiRepository mUserApiRepository;

    public ProfileViewModel() {
        mUserApiResponse = new MediatorLiveData<>();
        mUserApiRepository = new UserApiRepositoryImpl();
    }

    @NonNull
    public LiveData<UserApiResponse> getApiResponse() {
        return mUserApiResponse;
    }

    public LiveData<UserApiResponse> getUser() {
        mUserApiResponse.addSource(
                mUserApiRepository.getUser(),
                apiResponse -> mUserApiResponse.setValue(apiResponse)
        );
        return mUserApiResponse;
    }
}
