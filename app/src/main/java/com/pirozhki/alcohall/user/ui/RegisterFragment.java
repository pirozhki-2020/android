package com.pirozhki.alcohall.user.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pirozhki.alcohall.R;
import com.pirozhki.alcohall.user.viewmodel.AuthViewModel;

import java.util.regex.Pattern;

public class RegisterFragment extends Fragment {
    private String mEmailRegex = "^[a-zA-Z0-9\\-_]+[a-zA-Z0-9\\-_\\.]*@[a-zA-Z]+[a-zA-Z0-9\\.]+$";
    private String mPasswordRegex = "[!-~]{4,}";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_register, container, false);

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

                if (!s.toString().equals(passwordEditText.getText().toString())) {
                    repeatPasswordInputLayout.setError("Пароли не совпадают");
                } else {
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

            if (reap_password.equals(password))
                new ViewModelProvider(requireActivity()).get(AuthViewModel.class).register(email, password);
        });
        return view;
    }
}
