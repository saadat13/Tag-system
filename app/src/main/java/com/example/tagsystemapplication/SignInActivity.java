package com.example.tagsystemapplication;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.tagsystemapplication.Models.LoginResponse;
import com.example.tagsystemapplication.WebService.API_Client;
import com.example.tagsystemapplication.WebService.API_Interface;
import com.google.android.material.button.MaterialButton;

import java.util.HashMap;

import static com.example.tagsystemapplication.DataHolder.isConnectedToInternet;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_user;
    EditText et_pass;
    MaterialButton btn_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        initView();
    }

    private void initView() {
        et_user = findViewById(R.id.name);
        et_pass = findViewById(R.id.password);
        btn_signin = findViewById(R.id.sin);
        btn_signin.setOnClickListener(this);

        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        String username = pref.getString("username", "");
        String password = pref.getString("password", "");
        et_user.setText(username);
        et_pass.setText(password);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sin) {
            String user = et_user.getText().toString();
            String pass = et_pass.getText().toString();
            if (user.isEmpty()) {
                et_user.setError("please enter username");
                return;
            }else if (pass.isEmpty()) {
                et_pass.setError("please enter password");
                return;
            }

            // attempt login to server
            attemptSignIn(user, pass);
        }
    }

    private void saveUserData(String username, String password){
        //first save user data in storage for next usages
        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();
    }

    private void onSignInError(String username, String password) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("connection error")
                .setMessage("connecting to server failed")
                .setPositiveButton("Reload", (dialogInterface, i) -> {
                    attemptSignIn(username, password);
                    dialogInterface.dismiss();
                });
        builder.create().show();
    }

    private void attemptSignIn(String username, String password) {
        if(!isConnectedToInternet(this)) onSignInError(username, password);

        HashMap<String, String> userpass = new HashMap<>();
        userpass.put("username", username);
        userpass.put("password", password);

        API_Interface apiInterface = API_Client.getClient().create(API_Interface.class);
        Call<LoginResponse> call = apiInterface.getToken(userpass);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    saveUserData(username, password);
                    DataHolder.USER_SAVED_DATA = new String[]{username, password};
                    DataHolder.USER_RESPONSE = response.body();
                    // navigate user to processes activity
                    startActivity(new Intent(SignInActivity.this, ProcessActivity.class));

                }else{
                    Log.e("Response:::", response.message().toString());
                    onSignInError(username, password);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("Response:::", "");
                t.printStackTrace();
                onSignInError(username, password);
            }
        });

    }
}
