package com.example.tagsystemapplication.WebService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//make new retrofit object
public class API_Client {
    private static Retrofit retrofit = null;
    public static final String BASE_URL = "http://172.24.69.210:8000/";

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
