package com.pirozhki.alcohall.ingredients.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pirozhki.alcohall.R;

public class NoConnectionDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_no_connection, null);
        return new AlertDialog.Builder(requireActivity())
                .setView(v)
                .setNegativeButton(R.string.back_button, (dialog, id) -> dialog.cancel())
                .create();
    }
}