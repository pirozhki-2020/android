package com.pirozhki.alcohall.user.network;

import com.pirozhki.alcohall.user.model.User;
import com.squareup.moshi.Json;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserApi {

    @POST("/user.sign_up")
    Call<UserAnswer> regUser(@Body AuthBody body);

    @POST("/user.sign_in")
    Call<UserAnswer> loginUser(@Body AuthBody body);

    @GET("/user.get")
    Call<OneUser> getUser();

    @POST("/user.logout")
    Call<UserAnswer> logoutUser();

    class UserAnswer {
        @Json(name = "status")
        public String status;
        @Json(name = "message")
        public String message;
        @Json(name = "data")
        public User data;
    }

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

    class OneUser {
        @Json(name = "status")
        public String status;
        @Json(name = "data")
        public User data;
    }
}
