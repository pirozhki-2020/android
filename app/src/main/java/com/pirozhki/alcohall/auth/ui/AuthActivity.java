package com.pirozhki.alcohall.auth.ui;


import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.pirozhki.alcohall.R;
import com.pirozhki.alcohall.auth.model.User;
import com.pirozhki.alcohall.auth.viewmodel.AuthViewModel;
import com.pirozhki.alcohall.ingredients.ui.AddIngredientDialogFragment;
import com.pirozhki.alcohall.ingredients.ui.IngredientsActivity;
import com.pirozhki.alcohall.recipes.model.Recipe;
import com.pirozhki.alcohall.recipes.ui.RecipesActivity;
import com.pirozhki.alcohall.recipes.viewmodel.RecipeViewModel;

import java.util.List;

public class AuthActivity extends AppCompatActivity implements LoginFragment.Listener, RegisterFragment.Listener {
    private AuthViewModel mAuthViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity);

        final FragmentTransaction fragmentT = getSupportFragmentManager().beginTransaction();
        final RegisterFragment f = new RegisterFragment();
        fragmentT.add(R.id.auth_layout, f);
        fragmentT.commit();


        mAuthViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        mAuthViewModel.getApiResponse().observe(this, apiResponse -> {
            if (apiResponse.getError() != null) {
                handleError(apiResponse.getError());
            } else {
                handleResponse(apiResponse.getUser());
            }
        });

        final Button toLogButton = findViewById(R.id.to_login_button);
        final Button toRegisterButton = findViewById(R.id.to_register_button);
        toLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                final LoginFragment fragment = new LoginFragment();
                fragmentTransaction.replace(R.id.auth_layout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                toLogButton.setEnabled(false);
                toRegisterButton.setEnabled(true);

            }
        });
        toRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                final RegisterFragment fragment = new RegisterFragment();
                fragmentTransaction.replace(R.id.auth_layout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                toLogButton.setEnabled(true);
                toRegisterButton.setEnabled(false);

            }
        });

        final Button withoutAuthButton = findViewById(R.id.without_auth_button);

        withoutAuthButton.setOnClickListener( v -> {
            Intent intent = new Intent(this, IngredientsActivity.class);
            startActivity(intent);
        });

    }

    private void handleError(Throwable error) {
        Log.e(AddIngredientDialogFragment.class.getName(), "error occurred while get api response: " + error.toString());
        TextView errorView = findViewById(R.id.error_auth);
        errorView.setVisibility(View.VISIBLE);
        errorView.setText(error.toString());
    }

    private void handleResponse(User user) {
        if (user != null) {
            System.out.println(" handlerrr");
            System.out.println(user.getEmail());
            findViewById(R.id.error_auth).setVisibility(View.GONE);
            Intent intent = new Intent(this, IngredientsActivity.class);
            intent.putExtra("id", user.getId());
            startActivity(intent);
        } else {
            findViewById(R.id.error_auth).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLogin(String email, String password) {
        mAuthViewModel.login(email, password);
    }

    @Override
    public void onRegistration(String email, String password) {
        System.out.println(" in activity listener");
        mAuthViewModel.register(email, password);
    }
}
