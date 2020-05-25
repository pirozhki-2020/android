package com.pirozhki.alcohall.user.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private TextView mFirstNameTextView;
    private TextView mLastNameTextView;
    private TextView mEmailTextView;
    private TextView mErrorTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        if (!RetrofitInstance.haveCookies()) { // TODO: save cookies in android storage
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

            mFirstNameTextView = view.findViewById(R.id.user_first_name_text_view);
            mLastNameTextView = view.findViewById(R.id.user_last_name_text_view);
            mEmailTextView = view.findViewById(R.id.user_email_text_view);
            mErrorTextView = view.findViewById(R.id.error_profile_text_view);
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
            mFirstNameTextView.setText(user.getFirstName());
            mLastNameTextView.setText(user.getLastName());
            mEmailTextView.setText(user.getEmail());
        } else {
            mErrorTextView.setVisibility(View.VISIBLE);
        }
    }
}
