package com.pirozhki.alcohall.auth.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pirozhki.alcohall.R;
import com.pirozhki.alcohall.ingredients.model.Ingredient;
import com.pirozhki.alcohall.ingredients.ui.AddIngredientDialogFragment;

import java.util.regex.Pattern;

public class RegisterFragment extends Fragment {
    String mEmailRegex = "^[a-zA-Z0-9\\-_]+[a-zA-Z0-9\\-_\\.]*@[a-zA-Z]+[a-zA-Z0-9\\.]+$";
    String mPasswordRegex = "[!-~]{4,}";

    private Listener mListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.reg_fragment, null);



        final TextInputEditText emailEditText = view.findViewById(R.id.emailEditField_reg);
        final TextInputEditText passwordEditText = view.findViewById(R.id.passwordEditField_reg);
        final TextInputEditText repeatPasswordEditText = view.findViewById(R.id.repeatPasswordEditField);

        final TextInputLayout passwordInputLayout = view.findViewById(R.id.passwordTextField_reg);
        final TextInputLayout emailInputLayout = view.findViewById(R.id.emailTextField_reg);
        final TextInputLayout repeatPasswordInputLayout = view.findViewById(R.id.repeatPasswordTextField);

        assert passwordEditText != null;
        assert emailEditText != null;
        assert repeatPasswordEditText != null;

        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!Pattern.matches(mEmailRegex, s)) {
                    emailInputLayout.setError("Невалидный email!");
                } else {
                    emailInputLayout.setError(null);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!Pattern.matches(mPasswordRegex, s)) {
                    passwordInputLayout.setError("Пароль должен быть больше 4 символов");
                } else {
                    passwordInputLayout.setError(null);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        repeatPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().equals(passwordEditText.getText().toString())){
                    repeatPasswordInputLayout.setError("Пароли не совпадают");
                }  else {
                    repeatPasswordInputLayout.setError(null);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });


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
