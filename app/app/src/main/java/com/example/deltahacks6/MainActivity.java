package com.example.deltahacks6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView fromTextView;
    TextView toTextView;
    TextView amountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fromTextView = findViewById(R.id.sendFromAddress);
        String fromAddress = fromTextView.getText().toString();

        toTextView = findViewById(R.id.sendToAddress);
        String toAddress = toTextView.getText().toString();

        amountTextView = findViewById(R.id.amount);
        int amount =  Integer.parseInt(amountTextView.getText().toString());
    }

    public void onSendBtnPress(View v){

        String fromAddress = fromTextView.getText().toString();
        String toAddress = toTextView.getText().toString();
        int amount =  Integer.parseInt(amountTextView.getText().toString());



    }
}
