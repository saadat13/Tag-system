package com.example.tagsystemapplication.WebService;

import com.example.tagsystemapplication.Models.Output;
import com.example.tagsystemapplication.Models.Process;
import com.example.tagsystemapplication.Models.ProfilePackage;
import com.example.tagsystemapplication.Models.UserResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API_Interface {

//    @Header("content_type:application/json")
    @GET("accounts/token/")
    Call<UserResponse> getToken(@HeaderMap Map<String, String> headers);

    @GET("accounts/token/refresh/")
    Call<String> refreshToken(@HeaderMap Map<String, String> headers);


    @GET("api/processes/")
    Call<List<Process>> getProcesses();

    // TODO : in this step server sends a list of package profiles which are available and not tagged
    @GET("api/processes/{pid}/package_profiles/")
    Call<ProfilePackage> getPackageProfile(@Path("pid") int pid);


    @GET("api/processes/{pid}/package_profiles/{id}/profiles/{i}/block/")
    Call<String> blockProfile(@Path("pid") int pid,@Path("id") int id,@Path("i") int i);


    @POST("api/processes/{pid}/package_profiles/{id}/profiles/{i}/unblock/")
    Call<String> unblockProfile(@Path("pid") int pid,@Path("id") int id,@Path("i") int i);


//    @Headers("Content-Type: application/json")
    @POST("api/outputs/")
    Call<Output> sendOutput(@Body Output output);




}
