package com.pirozhki.alcohall.selections.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.pirozhki.alcohall.selections.network.OneSelectionApiResponse;
import com.pirozhki.alcohall.selections.network.SelectionApiRepo;
import com.pirozhki.alcohall.selections.network.SelectionApiRepoImpl;
import com.pirozhki.alcohall.selections.network.SelectionsApiResponse;

public class SelectionViewModel extends ViewModel {
    private MediatorLiveData<SelectionsApiResponse> mSelectionApiResponse;
    private SelectionApiRepo mSelectionApiRepository;
    private MediatorLiveData<OneSelectionApiResponse> mOneSelectionResponse;

    public SelectionViewModel() {
        mSelectionApiResponse = new MediatorLiveData<>();
        mSelectionApiRepository = new SelectionApiRepoImpl();
        mOneSelectionResponse = new MediatorLiveData<>();
    }

    @NonNull
    public LiveData<SelectionsApiResponse> getApiResponse() {
        return mSelectionApiResponse;
    }

    public LiveData<SelectionsApiResponse> getSelections(String from_id, String limit) {
        mSelectionApiResponse.addSource(
                mSelectionApiRepository.getSelections(from_id, limit),
                apiResponse -> mSelectionApiResponse.setValue(apiResponse)
        );
        return mSelectionApiResponse;
    }

    @NonNull
    public LiveData<OneSelectionApiResponse> getOneSelectionApiResponse() {
        return mOneSelectionResponse;
    }

    public LiveData<OneSelectionApiResponse> getOneSelection(@NonNull String id) {
        mOneSelectionResponse.addSource(
                mSelectionApiRepository.getOneSelection(id),
                apiResponse -> mOneSelectionResponse.setValue(apiResponse)
        );
        return mOneSelectionResponse;
    }
}
