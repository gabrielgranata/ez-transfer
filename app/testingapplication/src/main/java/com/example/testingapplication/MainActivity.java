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

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;



public class MainActivity extends AppCompatActivity {

    String TAG = "Testing";

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        System.out.println("Creating Account");

        try {
            Account account = new Account();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.err.println("Errorrrrrrr");
        }
//        try {
//            createAccount();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (GeneralSecurityException e) {
//            e.printStackTrace();
//        }
    }

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
