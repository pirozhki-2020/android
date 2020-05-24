package com.pirozhki.alcohall.selections.network;

import com.pirozhki.alcohall.selections.model.Selection;

import java.util.List;

public class SelectionsApiResponse {
    private List<Selection> mSelections;
    private Throwable mError;

    SelectionsApiResponse(List<Selection> selections) {
        mSelections = selections;
        mError = null;
    }

    SelectionsApiResponse(Throwable error) {
        mError = error;
        mSelections = null;
    }

    public List<Selection> getSelections() {
        return mSelections;
    }

    public Throwable getError() {
        return mError;
    }
}
