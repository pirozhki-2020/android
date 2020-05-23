package com.pirozhki.alcohall.auth.network;

import com.pirozhki.alcohall.auth.model.User;
import com.squareup.moshi.Json;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {

    @POST("/user.sign_up")
    Call<User> regUser(@Body AuthBody body);

    @POST("/user.sign_in")
    Call<User> loginUser(@Body AuthBody body);

    class AuthBody {
        @Json(name = "email")
        public String email;
        @Json(name = "password")
        public String password;

        public AuthBody(String e, String p) {
            email = e;
            password = p;
        }
    }
}
