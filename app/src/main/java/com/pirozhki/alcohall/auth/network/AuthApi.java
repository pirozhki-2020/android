package com.pirozhki.alcohall.auth.network;

import com.pirozhki.alcohall.auth.model.User;
import com.pirozhki.alcohall.ingredients.network.IngredientApi;
import com.squareup.moshi.Json;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {

    @POST("/user.sign_up")
    Call<UserAnswer> regUser(@Body AuthBody body);

    @POST("/user.sign_in")
    Call<UserAnswer> loginUser(@Body AuthBody body);


    class UserAnswer {
        @Json(name = "status")
        public String status;
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
}
