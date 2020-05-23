package com.pirozhki.alcohall.auth.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.pirozhki.alcohall.R;
import com.pirozhki.alcohall.ingredients.model.Ingredient;
import com.pirozhki.alcohall.ingredients.ui.AddIngredientDialogFragment;

public class RegisterFragment extends Fragment {

    private Listener mListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.reg_fragment, null);



        final TextInputEditText emailEditText = view.findViewById(R.id.emailEditField_reg);
        final TextInputEditText passwordEditText = view.findViewById(R.id.passwordEditField_reg);
        final TextInputEditText repeatPasswordEditText = view.findViewById(R.id.repeatPasswordEditField);

        assert passwordEditText != null;
        assert emailEditText != null;
        assert repeatPasswordEditText != null;


        final Button registerButton = view.findViewById(R.id.registration_button);

       registerButton.setOnClickListener(v -> {
           String email = emailEditText.getText().toString();
           String password = passwordEditText.getText().toString();
           String reap_password = repeatPasswordEditText.getText().toString();

           System.out.println(email);
           System.out.println(password);
           System.out.println(reap_password);

           if (reap_password.equals(password)){
               mListener.onRegistration(email, password);
           }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            mListener = (RegisterFragment.Listener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement");
        }
    }

    public interface Listener {
        void onRegistration(String email, String password);
    }
}
