package com.example.tagsystemapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.tagsystemapplication.Models.Process;
import com.example.tagsystemapplication.Models.Profile;
import com.example.tagsystemapplication.Models.LoginResponse;
import com.example.tagsystemapplication.WebService.API_Client;
import com.example.tagsystemapplication.WebService.API_Interface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataHolder{

    public static int currentProfileIndex = 0;
    public static int currentItemIndex = 0;
    public static int currentProcessIndex=0;
    public static List<Process> processes = new ArrayList<>();

    public static List<Profile> showingProfiles = new ArrayList<>();
    public static List<Profile> untaggedProfiles = new ArrayList<>();
    public static List<Profile> taggedProfiles = new ArrayList<>();

    public static LoginResponse USER_RESPONSE = null;
    public static String[] USER_SAVED_DATA = null;


    public static boolean isConnectedToInternet(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnected();
    }


    public static void reinitHeaders(Context context , String refreshToken){
        API_Interface apiInterface = API_Client.getClient(context).create(API_Interface.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("refresh", refreshToken);
        Call<LoginResponse> call = apiInterface.refreshToken(map);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    USER_RESPONSE = new LoginResponse();
                    USER_RESPONSE.setAccessToken(response.body().getAccessToken());
                    USER_RESPONSE.setRefreshToken(response.body().getRefreshToken());
                    SharedPreferences pref = context.getSharedPreferences("info", Context.MODE_PRIVATE);
                    pref.edit().putString("refresh", USER_RESPONSE.getRefreshToken()).apply();
                    pref.edit().putString("access", USER_RESPONSE.getAccessToken()).apply();
                    Log.wtf("TAG:::", "refresh token obtained successfully");
                }else if(response.code() == 401){ // refresh token is expired
                    //reSignIn(context);
                    // resign in is redundant
                } else{
                    Log.e("Response:::", "token reinitializing failed!!!");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("Response:::", "");
                t.printStackTrace();
            }
        });
    }


}
