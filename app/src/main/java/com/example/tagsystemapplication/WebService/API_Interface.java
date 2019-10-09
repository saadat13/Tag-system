package com.example.tagsystemapplication.WebService;

import com.example.tagsystemapplication.Models.AccessToken;
import com.example.tagsystemapplication.Models.Output;
import com.example.tagsystemapplication.Models.Process;
import com.example.tagsystemapplication.Models.ProfilePackage;
import com.example.tagsystemapplication.Models.LoginResponse;

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


    @POST("accounts/token/refresh/")
    Call<LoginResponse> refreshToken(@Header("refresh: ") String refreshToken);


    @GET("api/processes/")
    Call<List<Process>> getProcesses();

    // TODO : in this step server sends a list of package profiles which are available and not tagged
    @GET("api/processes/{pid}/package_profiles/")
    Call<ProfilePackage> getPackageProfile(@Path("pid") int pid);


    @POST("api/outputs/")
    Call<Output> sendOutput(@Body Output output);


//    @GET("api/processes/{pid}/package_profiles/{id}/profiles/{i}/block/")
//    Call<String> blockProfile(@Path("pid") int pid,@Path("id") int id,@Path("i") int i);
//
//
//    @POST("api/processes/{pid}/package_profiles/{id}/profiles/{i}/unblock/")
//    Call<String> unblockProfile(@Path("pid") int pid,@Path("id") int id,@Path("i") int i);

}
