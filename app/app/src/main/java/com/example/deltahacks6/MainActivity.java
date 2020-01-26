package com.example.deltahacks6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    TextView loginView;
    TextView passwordView;

    String fromAddress;
    String toAddress;
    int amount;

    String login;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginView = findViewById(R.id.emailHint);
        passwordView = findViewById(R.id.passwordHint);

    }



    public void makeRequest2() {
        try {
            URL url = new URL("http://10.0.2.2:3000/?login="+ login + "&password=" + password);
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

        login = loginView.getText().toString();
        password = passwordView.getText().toString();

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
