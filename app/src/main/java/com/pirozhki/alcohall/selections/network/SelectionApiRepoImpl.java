package com.pirozhki.alcohall.selections.network;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pirozhki.alcohall.common.RetrofitInstance;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class SelectionApiRepoImpl implements SelectionApiRepo {
    private SelectionApi mSelectionApi;

    public SelectionApiRepoImpl() {
        mSelectionApi = RetrofitInstance.getInstance().create(SelectionApi.class);
    }

    @Override
    public LiveData<OneSelectionApiResponse> getOneSelection(String id) {
        final MutableLiveData<OneSelectionApiResponse> liveData = new MutableLiveData<>();
        Call<SelectionApi.OneSelection> call = mSelectionApi.getSelection(id);
        call.enqueue(new Callback<SelectionApi.OneSelection>() {
            @Override
            public void onResponse(@NonNull Call<SelectionApi.OneSelection> call, @NonNull Response<SelectionApi.OneSelection> response) {
                if (response.body() == null) {
                    liveData.setValue(new OneSelectionApiResponse(new HttpException(response)));
                } else {
                    liveData.setValue(new OneSelectionApiResponse(Objects.requireNonNull(response.body()).data));
                }
            }

            @Override
            public void onFailure(@NonNull Call<SelectionApi.OneSelection> call, @NonNull Throwable t) {
                liveData.setValue(new OneSelectionApiResponse(t));
            }
        });
        return liveData;
    }

    @Override
    public LiveData<SelectionsApiResponse> getSelections(String from_id, String limit) {
        final MutableLiveData<SelectionsApiResponse> liveData = new MutableLiveData<>();
        Call<SelectionApi.Selections> call = mSelectionApi.getSelectionList(from_id, limit);
        call.enqueue(new Callback<SelectionApi.Selections>() {
            @Override
            public void onResponse(@NonNull Call<SelectionApi.Selections> call, @NonNull Response<SelectionApi.Selections> response) {
                if (response.body() == null) {

                    liveData.setValue(new SelectionsApiResponse(new HttpException(response)));
                }
                else {


                    liveData.setValue(new SelectionsApiResponse(response.body().data.selections));
                }
            }

            @Override
            public void onFailure(@NonNull Call<SelectionApi.Selections> call, @NonNull Throwable t) {
                liveData.setValue(new SelectionsApiResponse(t));
            }
        });
        return liveData;

    }
}
