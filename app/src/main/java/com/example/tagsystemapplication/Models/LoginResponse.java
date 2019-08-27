package com.example.tagsystemapplication.Models;


import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("role")
    private String role;

    @SerializedName("access")
    private String accessToken;

    @SerializedName("refresh")
    private String refreshToken;

    public LoginResponse(){}

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }





}
