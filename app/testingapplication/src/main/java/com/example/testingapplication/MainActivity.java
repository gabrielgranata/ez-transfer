package com.example.testingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;

import com.squareup.okhttp.HttpUrl;

import java.io.Console;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity {

    String toAddress = "hello";
    String fromAddress = "goodbye";
    int amount = 0;

    String TAG = "Testing";

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        System.out.println("Creating Account");

        executorService.execute(requestRunnable);
//        try {
//            Account account = new Account();
//            System.out.println(account.getAddress());
//            System.out.println(account.toMnemonic());
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            System.err.println("Errorrrrrrr");
//        }

//        try {
//            createAccount();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (GeneralSecurityException e) {
//            e.printStackTrace();
//        }
    }

    public void makeRequest() {
        try {
            System.out.println("ass");
            URL url = new URL("http://10.0.2.2:3000/?fromAddress="+ fromAddress + "&toAddress=" + toAddress + "&amount=" + amount);
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

    public void createAccount() throws IOException, GeneralSecurityException {

//        String email = "gabriel.granata@hotmail.com";
//        String password = "password";
//
//        auth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "Success");
//
//                        } else {
//                            Log.d(TAG, "Unsuccessful");
//                        }
//                    }
//                });
//        Account algorandAccount = new Account();
//        String mnemonic = algorandAccount.toMnemonic();
//        String address = algorandAccount.getAddress().toString();
    }
}
