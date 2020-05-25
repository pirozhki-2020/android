package com.pirozhki.alcohall.common;

import android.content.Context;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCookiesInterceptor implements Interceptor {
    private static final String PREF_COOKIES = "PREF_COOKIES";

    private Context mContext;

    AddCookiesInterceptor(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        HashSet<String> preferences = (HashSet<String>) PreferenceManager
                .getDefaultSharedPreferences(mContext).getStringSet(PREF_COOKIES, new HashSet<>());

        Request original = chain.request();
        if (!original.url().toString().contains("sign")) {
            for (String cookie : preferences) {
                builder.addHeader("Cookie", cookie);
            }
        }

        return chain.proceed(builder.build());
    }
}
