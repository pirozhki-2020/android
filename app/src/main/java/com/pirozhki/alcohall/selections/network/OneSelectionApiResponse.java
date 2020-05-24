package com.pirozhki.alcohall.selections.network;

import com.pirozhki.alcohall.selections.model.Selection;

public class OneSelectionApiResponse {
    private Selection mSelection;
    private Throwable mError;

    OneSelectionApiResponse(Selection selection) {
        mSelection = selection;
        mError = null;
    }

    OneSelectionApiResponse(Throwable error) {
        mError = error;
        mSelection = null;
    }

    public Selection getSelection() {
        return mSelection;
    }

    public Throwable getError() {
        return mError;
    }
}
