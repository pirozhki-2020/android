package android.pirozhki.alcohall.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.pirozhki.alcohall.R;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class NoResultDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.no_results_fragment, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setView(v)
        .setNegativeButton(R.string.back_button, (dialog, id) -> dialog.cancel());
        return builder.create();
    }
}
