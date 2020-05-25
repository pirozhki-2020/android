package com.pirozhki.alcohall.auth.model;

import com.squareup.moshi.Json;

public class User {
    @Json(name = "first_name")
    private String mFirstName;
    @Json(name = "last_name")
    private String mLastName;
    @Json(name = "email")
    private String mEmail;
    @Json(name = "username")
    private String mUsername;
    @Json(name = "id")
    private String mId;

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getId() {
        return mId;
    }
}
