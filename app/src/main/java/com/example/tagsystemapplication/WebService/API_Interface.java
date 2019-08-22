package com.example.tagsystemapplication.WebService;

import com.example.tagsystemapplication.Models.Process;
import com.example.tagsystemapplication.Models.ProfilePackage;
import com.example.tagsystemapplication.Models.Token;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API_Interface {

//    @Header("content_type:application/json")
    @GET("accounts/token/")
    Call<Token> getToken(@HeaderMap Map<String, String> headers);

    @GET("accounts/token/refresh/")
    Call<String> refreshToken(@HeaderMap Map<String, String> headers);


    @GET("api/processes/")
    Call<List<Process>> getProcesses();

    @GET("api/processes/{pid}/package_profiles/{id}/")
    Call<ProfilePackage> getPackageProfile(@Path("pid") int pid,@Path("id") int id);


    @GET("api/processes/{pid}/package_profiles/{id}/profiles/{i}/block/")
    Call<String> blockProfile(@Path("pid") int pid,@Path("id") int id,@Path("i") int i);


    @POST("api/processes/{pid}/package_profiles/{id}/profiles/{i}/unblock/")
    Call<String> unblockProfile(@Path("pid") int pid,@Path("id") int id,@Path("i") int i);


    @POST("api/outputs/")
    Call<String> sendOutput(@HeaderMap Map<String, String> headers);




}
