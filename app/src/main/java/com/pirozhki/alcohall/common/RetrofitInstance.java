package com.pirozhki.alcohall.common;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitInstance {
    private static final String HOST = "alcohall.space";
    private static final String SCHEMA = "https";
    private static final String PREF_COOKIES = "PREF_COOKIES";
    private static Retrofit INSTANCE = null;

    public static final String HOST_URL = SCHEMA + "://" + HOST;

    public static Retrofit getInstance() {
        if (INSTANCE == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(new AddCookiesInterceptor(App.getInstance()));
            builder.addInterceptor(new ReceivedCookiesInterceptor(App.getInstance()));
            OkHttpClient client = builder.build();

            INSTANCE = new Retrofit.Builder()
                    .addConverterFactory(MoshiConverterFactory.create())
                    .baseUrl(new HttpUrl.Builder().scheme(SCHEMA).host(HOST).addPathSegments("api/").build())
                    .client(client)
                    .build();
        }

        return INSTANCE;
    }

    public static boolean haveCookies() {
        HashSet<String> preferences = (HashSet<String>) PreferenceManager
                .getDefaultSharedPreferences(App.getInstance()).getStringSet(PREF_COOKIES, new HashSet<>());
        return preferences.size() > 0;
    }

    public static void deleteCookies() {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(App.getInstance()).edit();
        editor.clear().apply();
        editor.commit();
    }
}
