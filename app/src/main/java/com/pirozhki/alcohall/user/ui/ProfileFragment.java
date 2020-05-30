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
import androidx.lifecycle.ViewModelProvider;

import com.pirozhki.alcohall.R;
import com.pirozhki.alcohall.common.RetrofitInstance;
import com.pirozhki.alcohall.user.model.User;
import com.pirozhki.alcohall.user.viewmodel.ProfileViewModel;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class ProfileFragment extends Fragment {
    private ProfileViewModel mProfileViewModel;
    private TextView mEmailTextView;
    private TextView mErrorTextView;
    private Button mLogoutButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        if (!RetrofitInstance.haveCookies()) {
            findNavController(this).navigate(ProfileFragmentDirections.toAuthFragment());
        } else {
            mProfileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
            mProfileViewModel.getApiResponse().observe(getViewLifecycleOwner(), apiResponse -> {
                if (apiResponse.getError() != null) {
                    handleError(apiResponse.getError());
                } else {
                    handleResponse(apiResponse.getUser());
                }
            });
            mProfileViewModel.getUser();

            mEmailTextView = view.findViewById(R.id.user_email_text_view);
            mErrorTextView = view.findViewById(R.id.error_profile_text_view);
            mLogoutButton = view.findViewById(R.id.logout_button);

            mLogoutButton.setOnClickListener(v ->
            {
                mProfileViewModel.logout();
            });
        }

        return view;
    }

    private void handleError(Throwable error) {
        Log.e(ProfileFragment.class.getName(), "error occurred while get api response: " + error.getMessage());
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void handleResponse(User user) {
        if (user != null) {
            mErrorTextView.setVisibility(View.GONE);
            mEmailTextView.setText(user.getEmail());
        } else {
            mErrorTextView.setVisibility(View.VISIBLE);
            System.out.print("here");
            findNavController(this).navigate(ProfileFragmentDirections.toAuthFragment());
        }
    }
}
