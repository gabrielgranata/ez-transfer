package com.example.testingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;

import com.algorand.algosdk.account.Account;
import com.algorand.algosdk.crypto.Address;
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
            URL url = new URL("https://127.0.0.1:3000/");
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
        Account algorandAccount = new Account();
        String mnemonic = algorandAccount.toMnemonic();
        String address = algorandAccount.getAddress().toString();
    }
}
