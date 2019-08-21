package com.example.tagsystemapplication;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tagsystemapplication.WebService.API_Client;
import com.example.tagsystemapplication.WebService.API_Interface;

import java.util.HashMap;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_user;
    EditText et_pass;
    Button btn_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        // Apply activity transition
        // inside your activity (if you did not enable transitions in your theme)
//            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//            // set an exit transition
////            slide.setSlideEdge(Gravity.RIGHT);
//            getWindow().setExitTransition(Transition.);
//        } else {
//            // Swap without transition
//        }

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
            }
            if (pass.isEmpty()) {
                et_pass.setError("please enter password");
                return;
            }
            //TODO send user and pass to server if sign in was successful
            //TODO then username and password must be saved in system for next sign in's

            boolean register_flag = registerUser(user, pass);

            //if login was successful
            SharedPreferences pref = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("username", user);
            editor.putString("password", pass);
            editor.apply();

            // navigate user to processes activity
            Intent intent = new Intent(SignInActivity.this, ProcessActivity.class);
            startActivity(intent);
//            overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
        }
    }

    private boolean registerUser(final String username, String password) {
        boolean flag = false;
        API_Interface apiInterface = API_Client.getClient().create(API_Interface.class);


        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("username", username);
        hashMap.put("password", password);
        Call call = apiInterface.getToken(hashMap);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
        return flag;
    }
}
