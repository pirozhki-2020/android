package com.pirozhki.alcohall.common;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitInstance {
    private static final String HOST = "alcohall.space";
    private static final String SCHEMA = "https";
    private static final Retrofit INSTANCE = new Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(new HttpUrl.Builder().scheme(SCHEMA).host(HOST).build())
            .client(new OkHttpClient().newBuilder().cookieJar(new SessionCookieJar()).build())
            .build();

    private static List<Cookie> sCookies;

    public static final String HOST_URL = SCHEMA + "://" + HOST;

    public static Retrofit getInstance() {
        return INSTANCE;
    }

    public static boolean haveCookies() {
        return sCookies != null && sCookies.size() > 0;
    }

    private static class SessionCookieJar implements CookieJar {
        @Override
        public void saveFromResponse(HttpUrl url, @NonNull List<Cookie> cookies) {
            if (url.encodedPath().contains("sign")) {
                sCookies = new ArrayList<>(cookies);
            }
        }

        @NonNull
        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            if (!url.encodedPath().contains("sign") && sCookies != null) {
                return sCookies;
            }
            return Collections.emptyList();
        }
    }
}
