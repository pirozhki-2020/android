package com.pirozhki.alcohall.user.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.pirozhki.alcohall.R;
import com.pirozhki.alcohall.user.model.User;
import com.pirozhki.alcohall.user.viewmodel.AuthViewModel;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class AuthFragment extends Fragment {
    private AuthViewModel mAuthViewModel;
    private TextView mErrorTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_auth, container, false);

        final FragmentTransaction fragmentT = requireActivity().getSupportFragmentManager().beginTransaction();
        final RegisterFragment f = new RegisterFragment();
        fragmentT.add(R.id.auth_layout, f);
        fragmentT.commit();

        mAuthViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        mAuthViewModel.getApiResponse().observe(getViewLifecycleOwner(), apiResponse -> {
            if (apiResponse.getError() != null) {
                handleError(apiResponse.getError());
            } else {
                handleResponse(apiResponse.getUser());
            }
        });

        final Button toLogButton = view.findViewById(R.id.to_login_button);
        final Button toRegisterButton = view.findViewById(R.id.to_register_button);
        toLogButton.setOnClickListener(v -> {
            final FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
            final LoginFragment fragment = new LoginFragment();
            fragmentTransaction.replace(R.id.auth_layout, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            toLogButton.setEnabled(false);
            toRegisterButton.setEnabled(true);
        });
        toRegisterButton.setOnClickListener(v -> {
            final FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
            final RegisterFragment fragment = new RegisterFragment();
            fragmentTransaction.replace(R.id.auth_layout, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            toLogButton.setEnabled(true);
            toRegisterButton.setEnabled(false);
        });

        mErrorTextView = view.findViewById(R.id.error_auth_text_view);

        return view;
    }

    private void handleError(Throwable error) {
        Log.e(AuthFragment.class.getName(), "error occurred while get api response: " + error.getMessage());
        mErrorTextView.setVisibility(View.VISIBLE);
        if (error.getMessage() != null && error.getMessage().equals("Conflict")) {
            mErrorTextView.setText(R.string.email_exists);
        } else {
            mErrorTextView.setText(error.getMessage());
        }
    }

    private void handleResponse(User user) {
        if (user != null && !mAuthViewModel.hasCash(user)) {
            mErrorTextView.setVisibility(View.GONE);
            findNavController(this).navigate(AuthFragmentDirections.toProfileFragment());
        } else if (user == null) {
            mErrorTextView.setVisibility(View.VISIBLE);
        }
        mAuthViewModel.cashUser(user);
    }
}
