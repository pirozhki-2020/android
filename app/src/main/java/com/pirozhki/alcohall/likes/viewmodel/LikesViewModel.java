package com.pirozhki.alcohall.likes.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.pirozhki.alcohall.likes.network.LikesApiRepo;
import com.pirozhki.alcohall.likes.network.LikesApiRepoImpl;
import com.pirozhki.alcohall.likes.network.LikesApiResponse;

public class LikesViewModel extends ViewModel {
    private MediatorLiveData<LikesApiResponse> mLikesApiResponse;
    private LikesApiRepo mLikesApiRepository;

    public LikesViewModel() {
        mLikesApiResponse = new MediatorLiveData<>();
        mLikesApiRepository = new LikesApiRepoImpl();
    }

    @NonNull
    public LiveData<LikesApiResponse> getApiResponse() {
        return mLikesApiResponse;
    }

    public LiveData<LikesApiResponse> findRecipes() {
        mLikesApiResponse.addSource(
                mLikesApiRepository.findRecipes(),
                apiResponse -> mLikesApiResponse.setValue(apiResponse)
        );
        return mLikesApiResponse;
    }

}
