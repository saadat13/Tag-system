package com.example.tagsystemapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.tagsystemapplication.Models.AccessToken;
import com.example.tagsystemapplication.Models.Process;
import com.example.tagsystemapplication.Models.Profile;
import com.example.tagsystemapplication.Models.ProfilePackage;
import com.example.tagsystemapplication.Models.LoginResponse;
import com.example.tagsystemapplication.WebService.API_Client;
import com.example.tagsystemapplication.WebService.API_Interface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.prefs.Preferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataHolder{

//    public enum Roles{Expert, FullExpert}
//
//    public static Roles UserRole = Roles.Expert;  // default user role is expert

    public static int currentProfileIndex = 0;
    public static int currentItemIndex = 0;
    public static int currentProcessIndex=0;


    public static boolean isConnectedToInternet(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        return isConnected;
    }

    public static List<Process> processes = new ArrayList<>();
    public static List<Profile> profiles = new ArrayList<>();
    public static ProfilePackage currentProfilePackage = null;



    public static LoginResponse USER_RESPONSE = null;


    public static String[] USER_SAVED_DATA = null;



    public static void reinitHeaders(Context context , String refreshToken){
        API_Interface apiInterface = API_Client.getClient().create(API_Interface.class);
        Call<LoginResponse> call = apiInterface.refreshToken(refreshToken);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
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


//    private static void reSignIn(Context context) {
//        if(!isConnectedToInternet(context)) return;
//
//        HashMap<String, String> userpass = new HashMap<>();
//        userpass.put("username", USER_SAVED_DATA[0]);
//        userpass.put("password", USER_SAVED_DATA[1]);
//        API_Interface apiInterface = API_Client.getClient().create(API_Interface.class);
//        Call<LoginResponse> call = apiInterface.getToken(userpass);
//        call.enqueue(new Callback<LoginResponse>() {
//            @Override
//            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                if(response.isSuccessful()){
//                    DataHolder.USER_RESPONSE = response.body();
//                }else{
//                    Log.e("Response:::", response.message().toString());
//                }
//            }
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                Log.e("Response:::", "resignIn failed! ");
//                t.printStackTrace();
//            }
//        });
//
//    }

}
