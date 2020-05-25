package com.pirozhki.alcohall.selections.network;

import androidx.lifecycle.LiveData;

public interface SelectionApiRepo {

    LiveData<OneSelectionApiResponse> getOneSelection(String id);

    LiveData<SelectionsApiResponse> getSelections(String from_id, String limit);
}
