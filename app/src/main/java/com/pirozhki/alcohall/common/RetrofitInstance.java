package com.pirozhki.alcohall.common;

import okhttp3.HttpUrl;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitInstance {
    private static final String HOST = "alcohall.space";
    private static final Retrofit INSTANCE = new Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(new HttpUrl.Builder()
                    .scheme("https")
                    .host(HOST)
                    .build())
            .build();

    public static Retrofit getInstance() {
        return INSTANCE;
    }
}
