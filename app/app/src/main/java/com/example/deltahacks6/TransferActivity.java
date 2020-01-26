package com.example.deltahacks6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.algorand.algosdk.account.Account;
import com.algorand.algosdk.algod.client.AlgodClient;
import com.algorand.algosdk.algod.client.ApiException;
import com.algorand.algosdk.algod.client.api.AlgodApi;
import com.algorand.algosdk.algod.client.auth.ApiKeyAuth;
import com.algorand.algosdk.algod.client.model.TransactionID;
import com.algorand.algosdk.algod.client.model.TransactionParams;
import com.algorand.algosdk.crypto.Address;
import com.algorand.algosdk.crypto.Digest;
import com.algorand.algosdk.transaction.SignedTransaction;
import com.algorand.algosdk.transaction.Transaction;
import com.algorand.algosdk.util.Encoder;

import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransferActivity extends AppCompatActivity {

    TextView fromTextView;
    TextView toTextView;
    TextView amountTextView;
    private String fromAddress;
    private String toAddress;
    private int trueAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fromTextView = findViewById(R.id.sendFromAddress);
        toTextView = findViewById(R.id.sendToAddress);
        amountTextView = findViewById(R.id.amount);
    }

    public void onSendBtnPress(View v) throws Exception{

        fromAddress = fromTextView.getText().toString();
        toAddress = toTextView.getText().toString();
        trueAmount = Integer.parseInt(amountTextView.getText().toString());

        executorService.submit(requestRunnable);

    }


    public void makeRequest() {
        try {
            System.out.println("ass");
            URL url = new URL("http://10.0.2.2:3000/transaction?fromAddress="+ fromAddress + "&toAddress=" + toAddress + "&amount=" + trueAmount);
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
}

