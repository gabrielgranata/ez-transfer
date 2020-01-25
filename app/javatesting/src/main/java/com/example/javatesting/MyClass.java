package com.example.javatesting;

import java.math.BigInteger;

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

public class MyClass {

    public static void main(String args[]) throws Exception {
        final String ALGOD_API_ADDR = "https://testnet-algorand.api.purestake.io/ps1";
        final String ALGOD_API_TOKEN = "B3SU4KcVKi94Jap2VXkK83xx38bsv95K5UZm2lab";

        AlgodClient client = (AlgodClient) new AlgodClient().setBasePath(ALGOD_API_ADDR);
        ApiKeyAuth api_key = (ApiKeyAuth) client.getAuthentication("api_key");
        api_key.setApiKey(ALGOD_API_TOKEN);
        AlgodApi algodApiInstance = new AlgodApi(client);

        //Using a backup mnemonic to recover the source account to send tokens from
        final String SRC_ACCOUNT = "weird uncle argue later install proof canvas rabbit first own sorry anxiety gadget fiction devote planet clog smooth vanish element air couple argue ability inner";
        final String DEST_ADDR = "QACLCWJOTYTZSOJ5KUDYQC35TVV5LXHUY35SYDGH3P6FWIMXN26A4UH5KU";
        int AMOUNT = 5000000;


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
            // genesisID and genesisHash are optional on testnet, but will be mandatory on release
            // to ensure that transactions are valid for only a single chain. GenesisHash is preferred.
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
        Account src = new Account(SRC_ACCOUNT);
        BigInteger amount = BigInteger.valueOf(AMOUNT);
        BigInteger lastRound = firstRound.add(BigInteger.valueOf(1000)); // 1000 is the max tx window
        //Setup Transaction
        Transaction tx = new Transaction(src.getAddress(),  BigInteger.valueOf(1000), firstRound, lastRound, notes, amount, new Address(DEST_ADDR), genId, genesisHash);

        // Sign the Transaction
        SignedTransaction signedTx = src.signTransaction(tx);

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
        while(true) {
            try {
                //Check the pending tranactions
                com.algorand.algosdk.algod.client.model.Transaction b3 = algodApiInstance.pendingTransactionInformation(signedTx.transactionID);
                if (b3.getRound() != null && b3.getRound().longValue() > 0) {
                    System.out.println("Transaction " + b3.getTx() + " confirmed in round " + b3.getRound().longValue());
                    break;
                } else {
                    System.out.println("Waiting for confirmation... (pool error, if any:)" + b3.getPoolerror());
                }
            } catch (ApiException e) {
                System.err.println("Exception when calling algod#pendingTxInformation: " + e.getMessage());
                break;
            }
        }
        //Read the transaction
        try {
            com.algorand.algosdk.algod.client.model.Transaction rtx = algodApiInstance.transactionInformation(DEST_ADDR, signedTx.transactionID);
            System.out.println("Transaction information (with notes): " + rtx.toString());
            System.out.println("Decoded notes: [" + new String(rtx.getNoteb64()) + "]");
        } catch (ApiException e) {
            System.err.println("Exception when calling algod#transactionInformation: " + e.getCode());
        }
    }

}
