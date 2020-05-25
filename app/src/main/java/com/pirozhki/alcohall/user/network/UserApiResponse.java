package com.pirozhki.alcohall.user.network;

import com.pirozhki.alcohall.user.model.User;

public class UserApiResponse {
    private User mUser;
    private Throwable mError;
    private int mStatus;

    UserApiResponse(User user, int status) {
        mUser = user;
        mStatus = status;
        mError = null;
    }

    UserApiResponse(Throwable error) {
        mError = error;
        mUser = null;
    }

    public User getUser() {
        return mUser;
    }

    public int getStatus() {
        return mStatus;
    }

    public Throwable getError() {
        return mError;
    }
}
