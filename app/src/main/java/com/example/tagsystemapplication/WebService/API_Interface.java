package com.example.tagsystemapplication.WebService;

import com.example.tagsystemapplication.Models.Output;
import com.example.tagsystemapplication.Models.Process;
import com.example.tagsystemapplication.Models.LoginResponse;
import com.example.tagsystemapplication.Models.Profile;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API_Interface {

    @Headers("Content-Type: application/json")
    @POST("accounts/token/")
    Call<LoginResponse> getToken(@Body HashMap<String, String> userpass);

    @Headers("Content-Type: application/json")
    @POST("accounts/token/refresh/")
    Call<LoginResponse> refreshToken(@Body HashMap<String, String> refreshToken);


    @GET("api/processes/")
    Call<List<Process>> getProcesses();

    @GET("api/processes/{pk}/profiles/")
    Call<List<Profile>> getProfiles(@Path("pk") int pk);

    @Headers("Content-Type: application/json")
    @POST("api/outputs/")
    Call<JsonObject> sendOutput(@Body String output);


}
