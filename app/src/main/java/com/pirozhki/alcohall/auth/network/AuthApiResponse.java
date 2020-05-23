package com.pirozhki.alcohall.auth.network;

import com.pirozhki.alcohall.auth.model.User;

public class AuthApiResponse {
    User mUser;
    private Throwable mError;

    AuthApiResponse(User user) {
        mUser = user;
        mError = null;
    }

    AuthApiResponse(Throwable error) {
        mError = error;
        mUser = null;
    }

    public User getUser() {
        return mUser;
    }

    public Throwable getError() {
        return mError;
    }
}
