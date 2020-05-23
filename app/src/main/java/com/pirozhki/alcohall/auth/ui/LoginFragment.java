package com.pirozhki.alcohall.auth.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.pirozhki.alcohall.R;
import com.pirozhki.alcohall.ingredients.ui.AddIngredientDialogFragment;

public class LoginFragment extends Fragment {


    private Listener mListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.login_fragment, null);

        final TextInputEditText emailEditText = view.findViewById(R.id.emailEditField_login);
        final TextInputEditText passwordEditText = view.findViewById(R.id.passwordEditField_login);

        assert passwordEditText != null;
        assert emailEditText != null;

        final Button loginButton = view.findViewById(R.id.login_button);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            mListener = (LoginFragment.Listener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement");
        }
    }

    public interface Listener {
        void onLogin(String email, String password);
    }
}
