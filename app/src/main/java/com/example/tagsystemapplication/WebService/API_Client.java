package com.example.tagsystemapplication.WebService;

import android.content.Context;

import com.example.tagsystemapplication.DataHolder;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.example.tagsystemapplication.DataHolder.USER_RESPONSE;
import static com.example.tagsystemapplication.DataHolder.USER_SAVED_DATA;

//make new retrofit object
public class API_Client {
    private static Retrofit retrofit = null;
    public static final String BASE_URL = "http://192.168.120.13/";

    public static Retrofit getAuthorizedClient(Context context){
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        String accessToken = null;
        if(USER_RESPONSE == null)
            accessToken = context.getSharedPreferences("info", MODE_PRIVATE).getString("access", "");
        else
            accessToken = USER_RESPONSE.getAccessToken();
        String finalAccessToken = accessToken;
        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Authorization", "Bearer " + finalAccessToken)
                        .header("Content-Type", "application/json")
                        .build();
                return chain.proceed(request);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }
}
