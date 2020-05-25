package com.pirozhki.alcohall.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ReceivedCookiesInterceptor implements Interceptor {
    private static final String PREF_COOKIES = "PREF_COOKIES";

    private Context mContext;

    ReceivedCookiesInterceptor(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = (HashSet<String>) PreferenceManager
                    .getDefaultSharedPreferences(mContext).getStringSet(PREF_COOKIES, new HashSet<>());

            cookies.addAll(originalResponse.headers("Set-Cookie"));

            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
            editor.putStringSet(PREF_COOKIES, cookies).apply();
            editor.commit();
        }

        return originalResponse;
    }
}
