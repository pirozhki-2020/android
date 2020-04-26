package android.pirozhki.alcohall.network;


import android.content.Context;
import android.pirozhki.alcohall.ApplicationModified;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ApiRepo {
    private final IngrApi mIngrApi;
    private final OkHttpClient mOkHttpClient;
    private final String HOST = "http://alcohall.space";
    private final int PORT = 80;

    public ApiRepo() {
        mOkHttpClient = new OkHttpClient()
                .newBuilder()
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(new HttpUrl.Builder()
                        .scheme("http")
                        .host(HOST)
                        .port(PORT)
                        .build())
                .client(mOkHttpClient)
                .build();

        mIngrApi = retrofit.create(IngrApi.class);
    }

    public IngrApi getIngrApi() {
        return mIngrApi;
    }

    public static ApiRepo from(Context context) {
        return ApplicationModified.from(context).getApis();
    }
}
