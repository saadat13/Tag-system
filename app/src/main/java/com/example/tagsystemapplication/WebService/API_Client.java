package com.example.tagsystemapplication.WebService;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.example.tagsystemapplication.DataHolder.USER_RESPONSE;

//make new retrofit object
public class API_Client {
    private static Retrofit retrofit = null;
    private static final String DEFAULT_BASE_URL = "http://192.168.43.142:8000/";
    private static final String CONFIG_PATH = Environment.getExternalStorageDirectory().getPath().toString();
    private static String base_url = null;

    public static Retrofit getAuthorizedClient(Context context){
        prepareConfig(context);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        String accessToken = null;
        if(USER_RESPONSE == null)
            accessToken = context.getSharedPreferences("info", MODE_PRIVATE).getString("access", "");
        else
            accessToken = USER_RESPONSE.getAccessToken();
        String finalAccessToken = accessToken;
        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Authorization", "Bearer " + finalAccessToken)
                        .header("Content-Type", "application/json")
                        .build();
                return chain.proceed(request);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static Retrofit getClient(Context context) {
        prepareConfig(context);

        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }

    private static void prepareConfig(Context context){
        if(base_url != null) return;

        File file = new File(CONFIG_PATH + "/config.txt");
        if(file.exists()){
            base_url = loadConfig(context);
        }else{
            base_url = DEFAULT_BASE_URL;
            storeConfig(context);
        }
    }

    private static void storeConfig(Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(new File(CONFIG_PATH, "config.txt")));
            outputStreamWriter.write(DEFAULT_BASE_URL);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private static String loadConfig(Context context) {

        String ret = "";

        try {
            InputStream inputStream = new FileInputStream(new File(CONFIG_PATH, "config.txt"));
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString = "";
            StringBuilder stringBuilder = new StringBuilder();

            while ( (receiveString = bufferedReader.readLine()) != null ) {
                stringBuilder.append(receiveString);
            }

            inputStream.close();
            ret = stringBuilder.toString();

        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
}
