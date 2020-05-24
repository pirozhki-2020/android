package com.pirozhki.alcohall.selections.network;

import androidx.lifecycle.LiveData;

public interface SelectionApiRepo {

    LiveData<OneSelectionApiResponse> getOneSelection(Integer id);

    LiveData<SelectionsApiResponse> getSelections(Integer from_id, Integer limit);
}
