package com.pirozhki.alcohall.auth.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pirozhki.alcohall.R;
import com.pirozhki.alcohall.common.RetrofitInstance;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class ProfileFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        if (!RetrofitInstance.haveCookies()) {
            findNavController(this).navigate(ProfileFragmentDirections.toAuthFragment());
        }
        return view;
    }
}
