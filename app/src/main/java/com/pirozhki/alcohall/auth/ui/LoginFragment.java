package com.pirozhki.alcohall.auth.ui;

import android.content.Context;
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

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pirozhki.alcohall.R;
import com.pirozhki.alcohall.ingredients.ui.AddIngredientDialogFragment;

import java.util.Objects;
import java.util.regex.Pattern;

public class LoginFragment extends Fragment {
    String mEmailRegex = "^[a-zA-Z0-9\\-_]+[a-zA-Z0-9\\-_\\.]*@[a-zA-Z]+[a-zA-Z0-9\\.]+$";
    String mPasswordRegex = "[!-~]{4,}";
    private Listener mListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.login_fragment, null);

        final TextInputEditText emailEditText = view.findViewById(R.id.emailEditField_login);
        final TextInputLayout emailInputLayout = view.findViewById(R.id.emailTextField_login);
        final TextInputEditText passwordEditText = view.findViewById(R.id.passwordEditField_login);

        final TextInputLayout passwordInputLayout = view.findViewById(R.id.passwordTextField_login);

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



        final Button loginButton = view.findViewById(R.id.login_button);

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            System.out.println(email);
            System.out.println(password);

            mListener.onLogin(email, password);

        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (LoginFragment.Listener) context;
    }

    public interface Listener {
        void onLogin(String email, String password);
    }
}
