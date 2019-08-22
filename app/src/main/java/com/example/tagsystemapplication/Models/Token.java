package com.example.tagsystemapplication.Models;


import com.google.gson.annotations.SerializedName;

public class Token {

    @SerializedName("access")
    private String accessToken;

    @SerializedName("refresh")
    private String refreshToken;

}
