package com.example.deltahacks6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.View;
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
import java.security.GeneralSecurityException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    TextView fromTextView;
    TextView toTextView;
    TextView amountTextView;
    private String fromAddress;
    private String toAddress;
    private int trueAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fromTextView = findViewById(R.id.sendFromAddress);
        toTextView = findViewById(R.id.sendToAddress);
        amountTextView = findViewById(R.id.amount);
    }

    public void onSendBtnPress(View v) throws Exception{

        fromAddress = fromTextView.getText().toString();
        toAddress = toTextView.getText().toString();
        trueAmount = Integer.parseInt(amountTextView.getText().toString());

        executorService.submit(transactionRunnable);

        result.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });
    }

    public ExecutorService executorService = Executors.newSingleThreadExecutor();

    MutableLiveData<String> result = new MutableLiveData<>();

    Runnable transactionRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                makeTransaction(fromAddress, toAddress, trueAmount);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
        }
    };

    public void makeTransaction(String fromAccount, String toAccount, int trueAmount) throws IOException, GeneralSecurityException {

        // Algorand Hackathon
        final String ALGOD_API_ADDR = "http://hackathon.algodev.network:9100";
        final String ALGOD_API_TOKEN = "ef920e2e7e002953f4b29a8af720efe8e4ecc75ff102b165e0472834b25832c1";

        // your own node
        // final String ALGOD_API_ADDR = "http://localhost:8080";
        // final String ALGOD_API_TOKEN = "your ALGOD_API_TOKEN";

        // Purestake
        // final String ALGOD_API_ADDR =
        // "https://testnet-algorand.api.purestake.io/ps1";
        // final String ALGOD_API_TOKEN = "B3SU4KcVKi94Jap2VXkK83xx38bsv95K5UZm2lab";

        AlgodClient client = (AlgodClient) new AlgodClient().setBasePath(ALGOD_API_ADDR);
        // needed for Purestake , else ignored
        client.addDefaultHeader("X-API-Key", ALGOD_API_TOKEN);

        ApiKeyAuth api_key = (ApiKeyAuth) client.getAuthentication("api_key");
        api_key.setApiKey(ALGOD_API_TOKEN);
        AlgodApi algodApiInstance = new AlgodApi(client);

        // Using a backup mnemonic to recover the source account to send tokens from
        // get last round and suggested tx fee
        BigInteger suggestedFeePerByte = BigInteger.valueOf(1);
        BigInteger firstRound = BigInteger.valueOf(301);
        String genId = null;
        Digest genesisHash = null;
        try {
            // Get suggested parameters from the node
            TransactionParams params = algodApiInstance.transactionParams();
            suggestedFeePerByte = params.getFee();
            firstRound = params.getLastRound();
            System.out.println("Suggested Fee: " + suggestedFeePerByte);
            // genesisID and genesisHash are optional on testnet, but will be mandatory on
            // release
            // to ensure that transactions are valid for only a single chain. GenesisHash is
            // preferred.
            // genesisID will be deprecated soon.
            genId = params.getGenesisID();
            genesisHash = new Digest(params.getGenesishashb64());

        } catch (ApiException e) {
            System.err.println("Exception when calling algod#transactionParams");
            e.printStackTrace();
        }

        // add some notes to the transaction
        byte[] notes = "These are some notes encoded in some way!".getBytes();

        // Instantiate the transaction
        Account src = new Account(fromAccount);
        BigInteger amount = BigInteger.valueOf(trueAmount);
        BigInteger lastRound = firstRound.add(BigInteger.valueOf(1000)); // 1000 is the max tx window

        // Setup Transaction
        // Use a fee of 0 as we will set the fee per
        // byte when we sign the tx and overwrite it
        //

        Transaction tx = new Transaction(src.getAddress(), BigInteger.valueOf(0), firstRound, lastRound, notes, genId,
                genesisHash, amount, new Address(toAccount), null);

        // Sign the Transaction
        SignedTransaction signedTx = src.signTransactionWithFeePerByte(tx, suggestedFeePerByte);
        // how to sign offline
        // send the transaction to the network
        try {
            // Msgpack encode the signed transaction
            byte[] encodedTxBytes = Encoder.encodeToMsgPack(signedTx);
            TransactionID id = algodApiInstance.rawTransaction(encodedTxBytes);
            System.out.println("Successfully sent tx with id: " + id);
        } catch (ApiException e) {
            // This is generally expected, but should give us an informative error message.
            System.err.println("Exception when calling algod#rawTransaction: " + e.getResponseBody());
        }

        // wait for transaction to be confirmed
        while (true) {
            try {
                // Check the pending tranactions
                com.algorand.algosdk.algod.client.model.Transaction b3 = algodApiInstance
                        .pendingTransactionInformation(signedTx.transactionID);
                if (b3.getRound() != null && b3.getRound().longValue() > 0) {
                    System.out
                            .println("Transaction " + b3.getTx() + " confirmed in round " + b3.getRound().longValue());
                    break;
                } else {
                    System.out.println("Waiting for confirmation... (pool error, if any:)" + b3.getPoolerror());
                }
            } catch (ApiException e) {
                System.err.println("Exception when calling algod#pendingTxInformation: " + e.getMessage());
            }
        }

        // Read the transaction
        try {
            com.algorand.algosdk.algod.client.model.Transaction rtx = algodApiInstance.transactionInformation(toAccount,
                    signedTx.transactionID);
            System.out.println("Transaction information (with notes): " + rtx.toString());
            System.out.println("Decoded notes: [" + new String(rtx.getNoteb64()) + "]");
        } catch (ApiException e) {
            System.err.println("Exception when calling algod#transactionInformation: " + e.getCode());
        }
    }
}
