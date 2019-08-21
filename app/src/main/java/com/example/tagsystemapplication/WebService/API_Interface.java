package com.example.tagsystemapplication.WebService;

import com.example.tagsystemapplication.Models.Output;
import com.example.tagsystemapplication.Models.Process;
import com.example.tagsystemapplication.Models.ProfilePackage;

import java.util.List;
import java.util.Map;

import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API_Interface {

    @GET("accounts/token/")
    Call<String> getToken(@HeaderMap Map<String, String> headers);

    @GET("accounts/token/refresh/")
    Call<String> refreshToken(@HeaderMap Map<String, String> headers);


    @GET("api/processes/")
    Call<RealmList<Process>> getProcesses(@HeaderMap Map<String, String> headers);


    @GET("api/processes/{pid}/package_profiles/block/")
    Call<List<ProfilePackage>> blockProfile(@HeaderMap Map<String, String> headers,@Path("pid") int pid);


    @POST("api/processes/{pid}/package_profiles/unblock")
    Call unblockProfile(@HeaderMap Map<String, String> headers,@Path("pid") int pid);


    @POST("api/outputs/")
    Call sendOutput(@HeaderMap Map<String, String> headers);




}
