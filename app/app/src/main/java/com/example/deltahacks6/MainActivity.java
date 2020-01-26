package com.example.deltahacks6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    String fromAddress;
    String toAddress;
    int amount;

    String login;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        executorService.execute(requestRunnable);
    }

    public void makeRequest() {
        try {
            System.out.println("ass");
            URL url = new URL("http://10.0.2.2:3000/?param1="+ fromAddress + "&param2=" + toAddress + "&param3=" + amount);
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                System.out.println(connection.getResponseCode());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            System.out.println("Invalid host");
        }

    }
    public ExecutorService executorService = Executors.newSingleThreadExecutor();

    Runnable requestRunnable = new Runnable() {
        @Override
        public void run() {
            makeRequest();
        }
    };

    public void makeRequest2() {
        try {
            URL url = new URL("http://10.0.2.2:3000/?param1="+ login + "&param2=" + password);
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                System.out.println(connection.getResponseCode());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            System.out.println("Invalid host");
        }

    }

    public void onLoginButtonPress(View v){
        e2.execute(r2);

        startActivity(new Intent(MainActivity.this, TransferActivity.class));
    }

    public ExecutorService e2 = Executors.newSingleThreadExecutor();

    Runnable r2 = new Runnable() {
        @Override
        public void run() {
            makeRequest2();
        }
    };

}
